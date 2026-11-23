import json
import re
import pika
from jsonschema import validate, ValidationError

TRIAGE_OUTPUT_SCHEMA = {
    "type": "object",
    "properties": {
        "ticket_id": {"type": "string"},
        "categoria": {"type": "string", "enum": ["HARDWARE_PDV", "REDE_LOJA", "SISTEMA_ERP", "IMPRESSORA"]},
        "severidade": {"type": "string", "enum": ["BAIXA", "MEDIA", "ALTA", "CRITICA"]},
        "skill_requerida": {"type": "string"},
        "diagnostico_rag": {"type": "string"}
    },
    "required": ["ticket_id", "categoria", "severidade", "skill_requerida", "diagnostico_rag"],
    "additionalProperties": False
}

def sanitizar_pii(texto: str) -> str:
    texto = re.sub(r'\d{3}\.\d{3}\.\d{3}-\d{2}', '[CPF_OCULTO]', texto)
    texto = re.sub(r'[\w\.-]+@[\w\.-]+\.\w+', '[EMAIL_OCULTO]', texto)
    texto = re.sub(r'\(?\d{2}\)?\s?\d{4,5}-\d{4}', '[TELEFONE_OCULTO]', texto)
    return texto

def agente_classificador(descricao_limpa: str) -> dict:
    if "pdv" in descricao_limpa.lower() or "caixa" in descricao_limpa.lower():
        return {"categoria": "HARDWARE_PDV", "severidade": "CRITICA", "skill_requerida": "MANUTENCAO_PDV"}
    return {"categoria": "REDE_LOJA", "severidade": "ALTA", "skill_requerida": "INFRA_REDES"}

def agente_diagnosticador_rag(categoria: str) -> str:
    base_conhecimento = {
        "HARDWARE_PDV": "Verificar fonte 24V do terminal e reiniciar serviço SAT/NFC-e.",
        "REDE_LOJA": "Testar ping no gateway da loja e validar link redundante 4G."
    }
    return base_conhecimento.get(categoria, "Executar diagnóstico padrão Nível 1.")

def processar_mensagem(ch, method, properties, body):
    headers = properties.headers or {}
    tentativas = headers.get("x-retry-count", 0)

    try:
        evento = json.loads(body.decode("utf-8"))
        descricao_segura = sanitizar_pii(evento["descricao"])

        classificacao = agente_classificador(descricao_segura)
        diagnostico = agente_diagnosticador_rag(classificacao["categoria"])

        resultado = {
            "ticket_id": evento["ticket_id"],
            "categoria": classificacao["categoria"],
            "severidade": classificacao["severidade"],
            "skill_requerida": classificacao["skill_requerida"],
            "diagnostico_rag": diagnostico
        }

        validate(instance=resultado, schema=TRIAGE_OUTPUT_SCHEMA)

        ch.basic_publish(
            exchange="tickets.exchange",
            routing_key="ticket.classificado",
            body=json.dumps(resultado)
        )
        ch.basic_ack(delivery_tag=method.delivery_tag)

    except (ValidationError, Exception) as erro:
        if tentativas < 3:
            ttl_ms = (2 ** (tentativas + 1)) * 1000
            ch.basic_publish(
                exchange="tickets.retry.exchange",
                routing_key="ticket.triagem.retry",
                body=body,
                properties=pika.BasicProperties(
                    expiration=str(ttl_ms),
                    headers={"x-retry-count": tentativas + 1}
                )
            )
            ch.basic_ack(delivery_tag=method.delivery_tag)
        else:
            ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
