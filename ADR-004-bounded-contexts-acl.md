# ADR-004: Mapeamento de Contextos Delimitados e Camada Anti-Corrupção (ACL)
* **Status:** Aprovado | **Data:** 2026-10-28 | **Responsável:** Laura Martinson Vaz

## Contexto
O sistema legado de inventário das lojas utiliza contratos SOAP/XML antigos e instáveis.

## Decisão
Delimitar os *Bounded Contexts* e criar uma **Anti-Corruption Layer (ACL)** com padrão **Adapter** (`LegacyInventoryAdapter`) protegida por **Circuit Breaker** e **Fallback** (Resilience4j).

## Consequências e Trade-offs
* **Positivas:** Protege a Linguagem Ubíqua do núcleo e evita *thread starvation* quando o legado falha.
* **Negativas:** Sobrecarga de tradução de payloads na borda.
