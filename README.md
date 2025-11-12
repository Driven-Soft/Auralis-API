# 🌙 Auralis API - Global Solution

Bem-vindo(a) à **Auralis API**, o backend oficial do projeto Auralis

Este sistema foi desenvolvido para apoiar o **Auralis**, uma aplicação voltada ao **monitoramento de bem-estar, hábitos e engajamento dos usuários** — fornecendo uma base sólida de dados para análise e acompanhamento.

---

## 🎯 Objetivo

Criar uma **API REST** confiável, utilizando **Quarkus + Java + Oracle JDBC**, capaz de:

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
│   ├── UsuarioRepository.java
│   ├── RegistroRepository.java
│   ├── FeedbackRepository.java
│   └── InscricaoRepository.java
│
├── model/              # Entidades e DTOs
│   ├── Usuario.java
│   ├── Registro.java
│   ├── Feedback.java
│   └── Inscricao.java
```

---

## 🌐 Recursos da API

Todos os endpoints utilizam **RESTEasy (JAX-RS)** com **respostas em JSON (Jackson)**.  
Cada `Resource` se comunica com a camada `Business` para validação, e esta por sua vez chama o `Repository` para persistência no Oracle.

---

### ✅ `RegistroResource.java`

> Gerencia os registros diários de saúde dos usuários.

📌 **Endpoints:**
- `POST /registros` → cria um novo registro  
- `GET /registros` → retorna todos os registros  
- `GET /registros/{id}` → busca um registro por ID  

✔ **Campos esperados:**
```json
{
  "idUsuario": 13,
  "hidratacao": 2500,
  "tempo_sol": 45,
  "nivel_estresse": 3,
  "sono": 7.5,
  "tempo_tela": 5.2,
  "trabalho_horas": 8,
  "atividade_fisica": 60,
  "score": 85,
  "dataRegistro": "2025-11-12T10:00:00"
}
```

---

### ✅ `FeedbackResource.java`

> Armazena feedbacks de usuários sobre o sistema Auralis.

📌 **Endpoints:**
- `POST /feedbacks` → grava um novo feedback  
- `GET /feedbacks` → lista todos os feedbacks  

✔ **Campos esperados:**
```json
{
  "idUsuario": 3,
  "mensagem": "O atendimento foi excelente! Sistema rápido e fácil de usar.",
  "nota": 5,
  "dataFeedback": "2025-11-12T15:30:00"
}
```
---

### ✅ `UsuarioResource.java`

> Gerencia cadastro de usuários.

📌 **Endpoints:**
- `POST /usuarios` → cadastra novo usuário 
- `GET /usuarios` → lista todos os usuários existentes  

✔ **Campos esperados:**
```json
{
  "nome": "Henrique Cunha",
  "email": "henrique@email.com",
  "senha": "1234",
  "genero": "M",
  "nascimento": "2002-03-15"
}
```

---

### ✅ `InscricaoResource.java`

> Gerencia inscrições para notificações, lembretes e atualizações.

📌 **Endpoints:**
- `POST /inscricoes` → cadastra novo inscrito  
- `GET /inscricoes` → lista inscrições existentes  

✔ **Campos esperados:**
```json
{
  "idUsuario": 13,
  "whatsapp": "S",
  "email": "S",
  "status": "A"
}
```

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
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=RM565119
quarkus.datasource.password=*****
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
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

## 🌐 Repositório da API

🔗 [Auralis API](https://github.com/Driven-Soft/Auralis-API)

## 🌐 Repositório do Front-End

🔗 [Auralis](https://github.com/Driven-Soft/Auralis)

---

✨ Obrigado por conhecer nossa solução!
