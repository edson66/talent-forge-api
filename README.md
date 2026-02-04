#  TalentForge AI - API de Recrutamento Inteligente

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Spring AI](https://img.shields.io/badge/Spring_AI-Supported-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![Postgres](https://img.shields.io/badge/Postgres-16-blue)

**TalentForge** é uma API RESTful robusta desenvolvida para modernizar processos de recrutamento. O diferencial do projeto é a utilização de **Inteligência Artificial Generativa (LLM)** para analisar currículos em PDF, comparar com os requisitos da vaga e gerar um "Match Score" e feedback técnico automaticamente.

---

##  Demonstração e Documentação

A API é totalmente documentada via **Swagger / OpenAPI**.

![Swagger UI](src/main/resources/static/swagger.png)

### Exemplo de Análise da IA
Abaixo, o JSON de resposta gerado pela IA ao analisar um currículo PDF real:

![AI Response](src/main/resources/static/json.png)

---

##  Tecnologias Utilizadas

O projeto foi construído seguindo os princípios de **Clean Architecture** e **Domain-Driven Design (DDD)** para garantir desacoplamento e manutenibilidade.

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **IA & LLM:** Spring AI (Integrado com Groq/Llama 3 via driver OpenAI)
* **Banco de Dados:** PostgreSQL
* **Migração de Dados:** Flyway
* **Segurança:** Spring Security + JWT (Stateless)
* **Containerização:** Docker & Docker Compose
* **Documentação:** SpringDoc (Swagger UI)

---

##  Arquitetura do Projeto

O sistema está dividido em camadas para isolar regras de negócio de frameworks externos:

```text
com.talentForge.api
├── application      # Casos de uso (Services)
├── domain           # Entidades, Interfaces de Repositório e Regras de Negócio (Core)
└── infrastructure   # Implementações (JPA, Controllers, Configs, Security, External APIs)