# ADR-003: Definição dos Protocolos de Integração (REST, WebSocket e RabbitMQ com Retry/DLQ)
* **Status:** Aprovado | **Data:** 2026-10-20 | **Responsável:** Eduardo Willian

## Contexto
Necessidade de baixa latência na localização em tempo real dos consultores e garantia de entrega na triagem assíncrona.

## Decisão
Utilizar **REST/JSON** no gateway, **WebSockets** no `PresenceService` e **RabbitMQ** com filas de *Retry Exponencial* (2s, 4s, 8s) e *Dead Letter Queue* (`tickets.triage.dlq`).

## Consequências e Trade-offs
* **Positivas:** Isolamento de falhas transitórias e captura segura de *Poison Pills* na DLQ.
* **Negativas:** Exige consumidores idempotentes.
