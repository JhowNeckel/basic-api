# Basic API (Scala + Akka HTTP)

Projeto de exemplo de API REST em Scala, utilizando Akka HTTP e Jackson, organizado seguindo princípios de Clean Architecture.

O objetivo deste repositório é servir como base didática / template inicial para construção de APIs em Scala, com separação clara de responsabilidades, testes e suporte a Docker.

## 🧱 Arquitetura

O projeto está organizado em camadas bem definidas:
```
├── domain
│   ├── model         # Entidades de domínio
│   └── repository    # Contratos (interfaces)
│
├── application
│   ├── dto           # DTOs de entrada/saída
│   └── usecase       # Regras de negócio / serviços
│
├── infra
│   └── repository    # Implementações concretas (fake/in-memory)
│
├── interface
│   └── http
│       ├── routes    # Rotas HTTP (Akka HTTP)
│       └── support   # Serialização JSON (Jackson)
│
└── Main.scala        # Bootstrap da aplicação
```

Princípios seguidos

- Domínio independente de frameworks
- Camada de aplicação isolando regras de negócio
- Infraestrutura implementando contratos do domínio
- Interface HTTP desacoplada do core da aplicação

## 🚀 Tecnologias

- Scala 2.13
- Akka HTTP
- Akka Actor
- Jackson (serialização JSON)
- ScalaTest
- sbt
- Docker (sbt-native-packager)

## ▶️ Como rodar o projeto
Pré-requisitos

- JDK 11+
- sbt

Rodando localmente
```
sbt run
```

A aplicação irá subir em:
```
http://localhost:9000
```

## 📡 Endpoints disponíveis
Criar usuário
```http request
POST /users
```

Request body
```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

Responses

- `201 Created` – usuário criado
- `400 Bad Request` – dados inválidos

Listar usuários
```http request
GET /users
```

Response

```json
[
  {
    "id": "c8e1e62e-9a7d-4e4b-9d45-2c8a7a9b4c1f",
    "name": "John Doe"
  }
]
```

Buscar usuário por ID
```http request
GET /users/{id}
```

Responses

- `200 OK` – usuário encontrado
- `404 Not Found` – usuário não existe
- `400 Bad Request` – UUID inválido

Remover usuário
```http request
DELETE /users/{id}
```

Responses
- `200 No Content` – removido com sucesso
- `404 Not Found` – usuário não existe
- `400 Bad Request` – UUID inválido

## 🧪 Testes

O projeto possui testes unitários e de rotas.

Para rodar os testes:
```bash
sbt test
```
- Testes de service/usecase
- Testes de rotas HTTP usando Akka HTTP TestKit

## 🐳 Docker

A imagem Docker é gerada via sbt-native-packager.

Build da imagem
```bash
sbt docker:publishLocal
```

Executar container
```bash
docker run -p 9000:9000 basic-api:latest
```

## ⚠️ Observações importantes

- A persistência atual é in-memory (fake repository)
- Não há autenticação nem autorização
- O projeto é intencionalmente simples para fins didáticos