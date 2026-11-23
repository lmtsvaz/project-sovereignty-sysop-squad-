# ADR-005: Seleção de Frameworks, Banco Vetorial (Qdrant) e Stack do API Gateway
* **Status:** Aprovado | **Data:** 2026-11-05 | **Responsável:** Eduardo Willian / Eduardo Henrique

## Contexto
O roteamento exige leitura geoespacial sub-milissegundo e os agentes de IA precisam buscar manuais técnicos por similaridade semântica.

## Decisão
Adotar **Kong/NGINX** como API Gateway, **Redis Cluster** (`GEOADD`/`GEORADIUS`) para presença em memória e **Qdrant** como banco vetorial para RAG.

## Consequências e Trade-offs
* **Positivas:** Latência p95 < 250ms e alta precisão no diagnóstico técnico automatizado.
* **Negativas:** Necessidade de sincronização de embeddings quando manuais forem atualizados.
