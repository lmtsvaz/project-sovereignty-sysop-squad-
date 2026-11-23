# Checklist de Code Review & Governança Arquitetural (GitHub PR Template)


---

## Descrição da Alteração
<!-- VERSÃO DE CARREGAMENTO 01 DO PROJETO. -->

- **Tipo de Mudança:**
  - [ X ] `feat` (Nova funcionalidade de negócio)
  - [ X ] `arch` (Decisão de arquitetura / alteração de estrutura / ADR)
  - [  ] `refactor` (Refatoração para aplicação de SOLID / GoF)
  - [  ] `fix` (Correção de erro ou falha de integração)
  - [  ] `docs` (Atualização de diagramas C4 ou documentação)

- **Projeto da Equipe:** <!-- Code Review & Governança Arquitetural. -->

---

- **ADR(s) Relacionada(s):** <!-- ADR-001, ADR-002, ADR-003, ADR-004, ADR-005 e ADR-006 -->

---

## 1. Design & Princípios de Arquitetura (SOLID, GoF e DDD)

- [ x ] **Inversão de Dependência (DIP):** As classes da camada de Domínio dependem estritamente de abstrações (interfaces), sem acoplamento a frameworks, ORMs ou bancos de dados?
- [ x ] **Princípio Aberto/Fechado (OCP):** Novas funcionalidades foram introduzidas estendendo o comportamento através de polimorfismo/interfaces, sem modificar o código de produção estável?
- [ x ] **Padrões GoF Aplicados:** Os padrões de projeto GoF empregados (*Factory Method*, *Adapter*, *Strategy*, *State*, etc.) estão documentados e evitam sobre-engenharia?
- [ x ] **Linguagem Ubíqua (DDD):** Os nomes de entidades, Objeto de Valor (*Value Objects*), métodos e variáveis utilizam o vocabulário estrito do glossário de negócio?
- [ x ] **Contextos Delimitados (*Bounded Contexts*):** Não há vazamento de modelos ou tabelas entre diferentes subdomínios?
- [ x ] **Camada Anti-Corrupção (ACL):** Chamadas a APIs externas, mainframes ou sistemas legados foram encapsuladas por adaptadores de borda que protegem o *Core Domain*?

---

## 2. Modelo C4 & Documentação Técnica

- [ x ] **Diagramas C4 Atualizados:** Se a topologia de contêineres ou serviços mudou, os diagramas C4 no diretório `/docs/c4-model/` (Nível 1, Nível 2 ou Nível 3 em Mermaid.js/imagem) foram atualizados?
- [ x ] **Registro de Decisão Arquitetural (ADR):** Se este PR introduz uma nova tecnologia, biblioteca, protocolo ou padrão de mensageria, a ADR correspondente foi criada na pasta `/adrs/` com a seção de **Consequências e Trade-offs** preenchida?
- [x  ] **README Atualizado:** Os caminhos, links e tabelas de rastreabilidade de arquivos no `README.md` principal permanecem válidos?

---

## 3. Resiliência, Integração & Componentes IA-Native

- [ x ] **Contratos de Interface:** As APIs expostas ou consumidas (REST/JSON ou gRPC/Protobuf) possuem contratos formais atualizados?
- [ x ] **Tratamento de Falhas Parciais:** Chamadas remotas a serviços sujeitos a instabilidade possuem política de **Timeout**, **Retry com Backoff Exponencial** e **Circuit Breaker** (ex: Polly / Resilience4j)?
- [ x ] **Mensageria & Tolerância a Falhas:** As filas de integração (RabbitMQ/Kafka) contemplam tratamento de mensagens envencriptadas (*Poison Pills*) e roteamento para *Dead Letter Queue* (DLQ)?
- [ x ] **Componentes IA-Native:** Caso envolva chamadas a LLMs/SLMs, o prompt utiliza **Saídas Estruturadas (*Structured Outputs* com JSON Schema)** e possui validação de *Guardrails*?
- [  ] **Privacidade (PII):** Não há envio de dados pessoais identificáveis ou credenciais para modelos de IA de terceiros sem anonimização prévia?

---

## 4. Testes de Arquitetura & Qualidade de Código

- [ x ] **Fitness Functions (ArchUnit):** Os testes automatizados de arquitetura foram executados (`dotnet test` / `mvn test`) e passaram sem violação de dependências de camadas?
- [ x ] **Segurança & Credenciais:** O código foi verificado para garantir que **NENHUMA** chave de API, senha de banco de dados ou token privado foi commitado em *hardcode*?
- [ x ] **Métricas de Código:** O código alterado não eleva o acoplamento efferente de forma injustificada, mantendo a estabilidade (I) do núcleo do sistema?

---

## 5. Validação da Banca & Parecer do Revisor (Peer Review)

- **Revisor 1 (Membro da Equipe):** _____LAURA MARTINSON VAZ___________________ — Status: [ x ] Aprovado
- **Revisor 2 (Membro da Equipe) :** ____EDUARDO HENRIQUE DE OLIVEIRA SACOMORI___________________ — Status: [ x ] Aprovado
- - **Revisor 2 (Líder da Equipe) :** ________EDUARDO _WILLIAN PINTO_________________ — Status: [ x ] Aprovado

> **Observações do Revisor:**
> <!-- PRIMEIRA PUBLICAÇÃO DO NOSSO PROJETO, EM FASE DE VALIDAÇÃO COM NOSSO CLIENTE FINAL. -->
