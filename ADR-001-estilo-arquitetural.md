# ADR-001: Adoção de Arquitetura Baseada em Serviços Orientada a Eventos
* **Status:** Aprovado | **Data:** 2026-09-10 | **Responsável:** Laura Martinson Vaz / Eduardo Henrique

## Contexto
O sistema atende lojas distribuídas nacionalmente e precisa suportar picos severos de chamados (Black Friday) com alta disponibilidade e degradação graciosa.

## Decisão
Adotar Arquitetura Baseada em Serviços Orientada a Eventos, desacoplando `TicketService`, `RoutingEngine`, `PresenceService` e `AiTriageWorker` via `RabbitMQ`.

## Consequências e Trade-offs
* **Positivas:** Escalabilidade independente por serviço e absorção de picos de carga sem perda de tickets.
* **Negativas:** Maior complexidade operacional e necessidade de consistência eventual.
