# Assistente Corporativo 🤖

API REST de assistente inteligente para consulta de documentos empresariais com integração de IA.

## Sobre o Projeto

Imagine uma empresa que recebe centenas de documentos por dia — contratos, relatórios, PDFs — e quando precisa de uma informação específica, os funcionários precisam consultar cada arquivo manualmente. Ineficiente, né?

O **Assistente Corporativo** resolve esse problema permitindo fazer upload de documentos e simplesmente **perguntar sobre eles em linguagem natural**. O sistema processa os documentos, os divide em chunks e usa IA para responder perguntas com base no conteúdo.

Esse projeto implementa o conceito de **RAG (Retrieval-Augmented Generation)** — uma técnica que combina busca de informações relevantes com geração de respostas por IA.

## Funcionalidades

- Upload de documentos PDF
- Extração automática de texto dos PDFs
- Divisão do conteúdo em chunks de 500 palavras
- Armazenamento dos chunks no banco de dados
- Consulta por nome do documento
- Respostas geradas por IA com base no conteúdo dos documentos
- Listagem de documentos disponíveis

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.0.6**
- **Spring Data JPA**
- **Spring Web**
- **PostgreSQL**
- **Apache PDFBox 3.0.3** — extração de texto de PDFs
- **Gemini API (Google)** — geração de respostas com IA
- **Lombok**
- **Maven**

## Arquitetura RAG

```
Upload do PDF
      ↓
Extração de texto (PDFBox)
      ↓
Divisão em chunks de 500 palavras
      ↓
Armazenamento no PostgreSQL
      ↓
Usuário faz uma pergunta
      ↓
Sistema busca os chunks do documento
      ↓
Monta contexto + pergunta
      ↓
Gemini API gera a resposta
      ↓
Resposta retornada ao usuário
```

## Estrutura do Projeto

```
src/
├── controller/
│   └── DocumentController.java   # Endpoints da API
├── service/
│   └── DocumentService.java      # Lógica de negócio e integração com IA
├── repository/
│   ├── DocumentRepository.java   # Repositório de documentos
│   └── ChunksRepository.java     # Repositório de chunks
└── model/
    ├── Document.java             # Entidade de documento
    ├── Chunks.java               # Entidade de chunk
    └── PerguntaRequest.java      # DTO para requisição de perguntas
```

## Como Executar

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL
- Chave de API do Gemini (Google AI Studio)

### Configuração do Banco de Dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE assistente_corporativo;
```

### Configuração do application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/assistente_corporativo
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
gemini.api.key=sua_chave_do_gemini
server.port=3000
```

### Executando o Projeto

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:3000`.

## Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/documentos` | Upload de PDF |
| GET | `/documentos` | Listar todos os documentos |
| GET | `/documentos/nomes` | Listar nomes dos documentos |
| POST | `/documentos/pergunta` | Fazer uma pergunta sobre um documento |

## Exemplos de Uso

### Upload de PDF

```http
POST /documentos
Content-Type: multipart/form-data

arquivo: [arquivo.pdf]
```

Resposta:
```
documento salvo!!
```

### Listar Nomes dos Documentos

```http
GET /documentos/nomes
```

Resposta:
```json
[
    "relatorio-financeiro.pdf",
    "contrato-2024.pdf"
]
```

### Fazer uma Pergunta

```http
POST /documentos/pergunta
Content-Type: application/json

{
    "nomeDocumento": "relatorio-financeiro.pdf",
    "pergunta": "Qual foi o lucro do último trimestre?"
}
```

Resposta:
```
De acordo com o documento, o lucro do último trimestre foi de R$ 2.5 milhões...
```

## Como Funciona o Chunking

Documentos grandes são divididos em pedaços menores de 500 palavras para otimizar o processamento e a consulta:

```
Documento com 2000 palavras
         ↓
Chunk 1: palavras 0-499
Chunk 2: palavras 500-999
Chunk 3: palavras 1000-1499
Chunk 4: palavras 1500-1999
```

Cada chunk é armazenado no banco vinculado ao documento de origem, permitindo buscas eficientes.

## Aprendizados

Este projeto foi desenvolvido com foco no aprendizado de:

- Construção de APIs REST com Spring Boot
- Integração com IA generativa (Gemini API)
- Upload e processamento de PDFs com Apache PDFBox
- Arquitetura RAG (Retrieval-Augmented Generation)
- Arquitetura em camadas (Controller, Service, Repository)
- Persistência de dados com JPA e PostgreSQL
- Relacionamento entre entidades (`@ManyToOne`)
- Uso de DTOs para requisições

## Autor

Raimundo Neto — [GitHub](https://github.com/netuon)
