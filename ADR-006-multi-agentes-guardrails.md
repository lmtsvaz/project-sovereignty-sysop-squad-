# ADR-006: Integração de Ecossistema Multi-Agente de IA e Guardrails de Segurança
* **Status:** Aprovado | **Data:** 2026-11-15 | **Responsável:** Eduardo Willian

## Contexto
Automatizar a triagem sem expor dados sensíveis (LGPD/PII) nem quebrar o fluxo com respostas não estruturadas do LLM.

## Decisão
Orquestrar dois agentes (Classificador + Diagnosticador RAG) com sanitização prévia de PII via regex e validação estrita de saída via **JSON Schema**.

## Consequências e Trade-offs
* **Positivas:** Conformidade com LGPD e integração determinística com o motor de roteamento.
* **Negativas:** Latência adicional de ~1,5s na triagem, absorvida pelo fluxo assíncrono no RabbitMQ.
