# ADR-002: Desacoplamento de Núcleo com DIP (D = 0) e Padrões GoF (State e Strategy)
* **Status:** Aprovado | **Data:** 2026-10-05 | **Responsável:** Laura Martinson Vaz

## Contexto
A auditoria da Sprint 2 identificou acoplamento direto entre serviços concretos ($D > 0$), além de condicionais complexas nas transições de estado do chamado e regras de contingência.

## Decisão
Aplicar **DIP** com portas no domínio (`D = 0,00`), **GoF State** na entidade `Ticket` e **GoF Strategy** no `RoutingEngine`.

## Consequências e Trade-offs
* **Positivas:** Domínio isolado de frameworks externos, alta testabilidade e aderência ao OCP.
* **Negativas:** Aumento da quantidade inicial de classes e interfaces.
