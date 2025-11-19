# 🌙 Auralis API - `Global Solution 2025/2`

Bem-vindo(a) à **Auralis API**, o backend oficial do projeto Auralis

Este sistema foi desenvolvido para apoiar o **Auralis**, uma aplicação voltada ao **monitoramento de bem-estar, hábitos e engajamento dos usuários** — fornecendo uma base sólida de dados para análise e acompanhamento.

---

## 🎯 Objetivo

Criar uma **API REST** confiável, utilizando **Quarkus + Java + Oracle JDBC**, capaz de:

- Cadastrar novos usuários na plataforma
- Registrar dados de rotina e saúde dos usuários
- Armazenar feedbacks sobre a experiência de uso
- Gerenciar inscrições em notificações e lembretes
- Integrar-se ao **Front-End Auralis** de forma segura e performática

---

## 🗂 Estrutura do Projeto

```
src/main/java/br/com/fiap/
├── resource/           # Endpoints REST
│   ├── UsuarioResource.java
│   ├── RegistroResource.java
│   ├── FeedbackResource.java
│   └── InscricaoResource.java
│
├── business/           # Regras de negócio e validações
│   ├── UsuarioBusiness.java
│   ├── RegistroBusiness.java
│   ├── FeedbackBusiness.java
│   └── InscricaoBusiness.java
│
├── repository/         # Persistência Oracle via JDBC
│   ├── ConnectionFactory.java
│   ├── UsuarioRepository.java
│   ├── RegistroRepository.java
│   ├── FeedbackRepository.java
│   └── InscricaoRepository.java
│
├── model/              # Entidades
│   ├── Usuario.java
│   ├── Registro.java
│   ├── Feedback.java
│   └── Inscricao.java
│
└── DTO/                # DTOs
    └── LoginDTO.java
```

---

## 🌐 Recursos da API

Todos os endpoints utilizam **REST Jackson** com **respostas em JSON (Jackson)**.
Cada `Resource` se comunica com a camada `Business` para validação, e esta por sua vez chama o `Repository` para persistência no Oracle.

---

### ✅ `RegistroResource.java`

> Gerencia os registros diários de saúde dos usuários.

## 📌 **Endpoints:**
| Método | URI                | Descrição                        | Status Esperados         |
|--------|--------------------|----------------------------------|-------------------------|
| GET    | /registros         | Lista todos os registros          | 200, 500                |
| GET    | /registros/{id}    | Busca registro por ID             | 200, 404, 500           |
| POST   | /registros         | Cria novo registro                | 201, 400, 409, 500      |
| PUT    | /registros/{id}    | Atualiza registro existente       | 200, 400, 404, 500      |
| DELETE | /registros/{id}    | Remove registro por ID            | 204, 404, 500           |


✔ **Campos esperados:**
```json
{
  "idRegistro": 1,
  "idUsuario": 13,
  "hidratacao": 2500,
  "tempo_sol": 45,
  "nivel_estresse": 3,
  "sono": 7.5,
  "tempo_tela": 5.2,
  "trabalho_horas": 8.0,
  "atividade_fisica": 60,
  "score": 85,
  "dataRegistro": "2025-11-12T10:00:00"
}
```

---

### ✅ `FeedbackResource.java`

> Armazena feedbacks de usuários sobre o sistema Auralis.

## 📌 **Endpoints:**
| Método | URI                | Descrição                    | Status Esperados         |
|--------|--------------------|-----------------------------|-------------------------|
| GET    | /feedbacks         | Lista todos os feedbacks     | 200, 500                |
| GET    | /feedbacks/{id}    | Busca feedback por ID        | 200, 404, 500           |
| POST   | /feedbacks         | Cria novo feedback           | 201, 400, 500           |
| PUT    | /feedbacks/{id}    | Atualiza feedback existente  | 200, 400, 404, 500      |
| DELETE | /feedbacks/{id}    | Remove feedback por ID       | 204, 404, 500           |


✔ **Campos esperados:**
```json
{
  "idFeedback": 1,
  "idUsuario": 3,
  "mensagem": "O atendimento foi excelente! Sistema rápido e fácil de usar.",
  "nota": 5,
  "dataFeedback": "2025-11-12T15:30:00"
}
```
---

### ✅ `UsuarioResource.java`

> Gerencia cadastro de usuários.

## 📌 **Endpoints:**
| Método | URI                | Descrição                        | Status Esperados         |
|--------|--------------------|----------------------------------|-------------------------|
| GET    | /usuarios          | Lista todos os usuários           | 200, 500                |
| GET    | /usuarios/{id}     | Busca usuário por ID              | 200, 404, 500           |
| POST   | /usuarios          | Cria novo usuário                 | 201, 400, 500           |
| PUT    | /usuarios/{id}     | Atualiza usuário existente        | 200, 400, 404, 500      |
| DELETE | /usuarios/{id}     | Remove usuário por ID             | 204, 404, 500           |

✔ **Campos esperados:**
```json
{
  "idUsuario": 13,
  "nome": "Henrique Cunha",
  "email": "henrique@email.com",
  "senha": "1234",
  "telefone": "11999999999",
  "genero": "M",
  "nascimento": "2002-03-15",
  "dataCadastro": "2025-11-12T09:00:00"
}
```

---

### ✅ `InscricaoResource.java`

> Gerencia inscrições para notificações, lembretes e atualizações.

## 📌 **Endpoints:**
| Método | URI                | Descrição                        | Status Esperados         |
|--------|--------------------|----------------------------------|-------------------------|
| GET    | /inscricoes        | Lista todas as inscrições         | 200, 500                |
| GET    | /inscricoes/{id}   | Busca inscrição por ID            | 200, 404, 500           |
| POST   | /inscricoes        | Cria nova inscrição               | 201, 400, 409, 500      |
| PUT    | /inscricoes/{id}   | Atualiza inscrição existente      | 200, 400, 404, 500      |
| DELETE | /inscricoes/{id}   | Remove inscrição por ID           | 204, 404, 500           |

✔ **Campos esperados:**
```json
{
  "idInscricao": 1,
  "idUsuario": 13,
  "whatsapp": "S",
  "email": "S",
  "dataInscricao": "2025-11-12T09:30:00",
  "status": "A"
}
```

---

### ✅ `CORS`

> Gerencia permissões envolvendo as requisições.

## 📌 **Endpoints:**
| Método | URI                | Descrição                        | Status Esperados         |
|--------|--------------------|----------------------------------|-------------------------|
| Todos  | Todos              | Permite requisições de origens específicas e métodos HTTP na API | 200, 401, 403           |

---

## 🧠 Camada Business

Cada `Business` aplica **validações e regras de negócio**, como:

✔ Verificar se o usuário existe
✔ Impedir valores fora dos limites (ex: nível de estresse)
✔ Garantir consistência nos dados antes da gravação
✔ Retornar mensagens de erro claras em caso de falha

Fluxo geral:
1. **Resource** recebe a requisição JSON
2. **Business** valida os dados
3. **Repository** executa o SQL no Oracle
4. Resposta JSON é retornada ao front-end

---

## 🚀 Como Executar Localmente

```bash
git clone https://github.com/auralis/api
cd auralis-api
mvn quarkus:dev
```

A API ficará disponível em:

```
http://localhost:8080
```

### ⚙️ Configuração do `application.properties`

```properties
quarkus.http.cors=true
quarkus.http.cors.origins=https://auralis-gs.vercel.app,http://localhost:5173
quarkus.http.cors.methods=GET,POST,PUT,DELETE,HEAD,OPTIONS
quarkus.http.cors.headers=Accept,Authorization,Content-Type,Origin
quarkus.http.cors.access-control-allow-credentials=false
```

---

## 🛠 Tecnologias Utilizadas

| Tecnologia             | Função                                     |
| ---------------------- | ------------------------------------------ |
| **Quarkus**            | Framework principal para APIs REST         |
| **Java 17**            | Linguagem utilizada                        |
| **Maven**              | Gerenciamento de dependências              |
| **RESTEasy + Jackson** | Controle REST + serialização JSON          |
| **Oracle JDBC**        | Conexão e persistência em banco Oracle     |

---

## 👥 Equipe

| Integrante | RM | Função |
|-------------|------|----------------|
| **🧑‍🎨 Henrique Cunha Torres** | 565119 | Backend Developer |
| **👨‍💻 Felipe Bezerra Beatriz** | 564723 | Frontend Developer |
| **👨‍🔬 Max Hayashi Batista** | 563717 | Database |

---

## 🐦‍🔥 Link da API no Render

🔗 [Render](https://auralis-api.onrender.com/)

## 🔥 Repositório do Front-End

🔗 [Auralis](https://github.com/Driven-Soft/Auralis)

## 🎬 Vídeo Pitch do Projeto

🔗 [Pitch](https://www.youtube.com/watch?v=-SazkAV5Uns)

---

✨ Obrigado por conhecer nossa solução!
