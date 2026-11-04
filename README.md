# Subscription APIs

API REST desenvolvida com Java e Spring Boot para gerenciamento de clientes e suas assinaturas de serviços.

## 1. Descrição e objetivo do sistema

O **Subscription APIs** é um sistema de gerenciamento de assinaturas que permite cadastrar clientes, registrar suas assinaturas e acompanhar informações relacionadas aos serviços contratados.

O sistema foi desenvolvido com o objetivo de aplicar conceitos de desenvolvimento de APIs REST, persistência de dados, validação de informações, regras de negócio e arquitetura em camadas.

### Principais funcionalidades

- Cadastro de clientes.
- Consulta de clientes por ID.
- Atualização de dados dos clientes.
- Exclusão de clientes, respeitando as regras de negócio.
- Atualização de senhas com validação de segurança.
- Cadastro de assinaturas.
- Consulta de assinaturas por ID.
- Listagem de assinaturas.
- Pesquisa por nome e dia de pagamento.
- Atualização e exclusão de assinaturas.
- Gerenciamento de assinaturas vinculadas a clientes.
- Documentação da API com Swagger/OpenAPI.

## 2. Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem de programação |
| Spring Boot 4.1.1 | Framework para desenvolvimento da API |
| Spring Web MVC | Criação dos endpoints REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | ORM para mapeamento entre objetos Java e banco de dados |
| PostgreSQL | Banco de dados relacional |
| Docker | Execução do banco de dados em container |
| Maven | Gerenciamento de dependências e build |
| Jakarta Validation | Validação de dados |
| Springdoc OpenAPI | Documentação interativa da API |
| BCrypt | Hash de senhas |
| Lombok | Redução de código boilerplate |

## 3. Arquitetura do projeto

O projeto utiliza uma arquitetura organizada em camadas, separando as responsabilidades da aplicação.

```text
src/
├── main/
│   ├── java/
│   │   └── com/vithgab/subscription_apis/
│   │       ├── clientsDTO/
│   │       ├── controller/
│   │       │   └── Config/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       ├── subscriptionsDTO/
│   │       └── SubscriptionApisApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

### Responsabilidades das camadas

- **Controller:** recebe as requisições HTTP e retorna as respostas da API.
- **Service:** concentra as regras de negócio e a lógica da aplicação.
- **Repository:** realiza operações de persistência no banco de dados.
- **Model:** representa as entidades do sistema.
- **DTO:** define os dados utilizados na entrada e saída das requisições.
- **Exception:** centraliza o tratamento de erros da aplicação.

## 4. Pré-requisitos

Antes de executar o projeto, é necessário possuir:

- Java JDK 21.
- Docker Desktop.
- Git.
- IDE de sua preferência, como Visual Studio Code ou IntelliJ IDEA.

O Maven Wrapper já está incluído no projeto, portanto não é necessário instalar o Maven globalmente.

## 5. Como executar o projeto

### 5.1 Clonar o repositório

```bash
git clone https://github.com/VithGab/subscription-apis.git
```

Acesse a pasta do projeto:

```bash
cd subscription-apis
```

### 5.2 Iniciar o banco de dados

O projeto possui um arquivo `docker-compose.yml` responsável pela configuração do PostgreSQL.

Execute:

```bash
docker compose up -d
```

Verifique se o container está em execução:

```bash
docker ps
```

O container utilizado pelo projeto é:

```text
subscription-postgres-spring
```

### 5.3 Executar a aplicação

No Windows, utilizando PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou Ubuntu:

```bash
./mvnw spring-boot:run
```

A aplicação será executada na porta 8080.

URL base:

```text
http://localhost:8080
```

## 6. Configuração do banco de dados

O sistema utiliza PostgreSQL como banco de dados relacional.

A configuração é realizada por meio do Docker Compose e do arquivo `application.properties`.

### 6.1 Configuração do Docker Compose

O arquivo `docker-compose.yml` contém as seguintes configurações:

```yaml
services:
  db:
    image: postgres:latest
    container_name: subscription-postgres-spring
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: root
      POSTGRES_DB: subscription_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql

volumes:
  postgres_data:
```

### 6.2 Configuração da aplicação

Arquivo:

`src/main/resources/application.properties`

```properties
spring.application.name=subscription-apis

spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_db
spring.datasource.username=admin
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 6.3 Parâmetros de conexão

| Parâmetro | Valor |
|---|---|
| SGBD | PostgreSQL |
| Host | localhost |
| Porta | 5432 |
| Banco de dados | subscription_db |
| Usuário | admin |
| Senha | root |

O Hibernate realiza o mapeamento das entidades Java para as tabelas do banco de dados.

A configuração `spring.jpa.hibernate.ddl-auto=update` permite atualizar a estrutura das tabelas conforme as entidades da aplicação.

**Observação:** as credenciais apresentadas são destinadas ao ambiente de desenvolvimento. Em produção, devem ser utilizadas variáveis de ambiente e credenciais seguras.

## 7. Documentação Swagger / OpenAPI

O projeto utiliza Springdoc OpenAPI para gerar a documentação da API.

Com a aplicação em execução, acesse:

### Swagger UI

http://localhost:8080/swagger-ui/index.html

A interface permite visualizar os endpoints, consultar os parâmetros, verificar os modelos de dados e executar requisições diretamente pelo navegador.

### OpenAPI JSON

http://localhost:8080/v3/api-docs

Esse endereço retorna a documentação da API em formato JSON.

### Como obter o endereço Swagger?

1. Execute o projeto com o Spring Boot.
2. Abra o navegador.
3. Acesse `http://localhost:8080/swagger-ui/index.html`.
4. Utilize a interface para consultar e testar os endpoints.

Caso a documentação não abra, verifique se a aplicação iniciou corretamente e se a dependência `springdoc-openapi-starter-webmvc-ui` está presente no `pom.xml`.

## 8. Principais endpoints

A API possui endpoints para gerenciamento de clientes e assinaturas.

### 8.1 Clientes

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/clients` | Cadastrar cliente |
| GET | `/clients/{id}` | Consultar cliente por ID |
| PUT | `/clients/{id}` | Atualizar cliente |
| DELETE | `/clients/{id}` | Excluir cliente |
| PATCH | `/clients/{id}/password` | Atualizar senha |

### 8.2 Assinaturas

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/subscriptions` | Criar assinatura |
| GET | `/subscriptions/{id}` | Consultar assinatura por ID |
| GET | `/subscriptions/subscription` | Listar todas as assinaturas |
| GET | `/subscriptions/name/{name}` | Filtrar assinaturas por nome |
| GET | `/subscriptions/paymentDay/{paymentDay}` | Filtrar pelo dia de pagamento |
| PUT | `/subscriptions/{id}` | Atualizar assinatura |
| DELETE | `/subscriptions/{id}` | Excluir assinatura |

### 8.3 Assinaturas de um cliente

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/clients/{clientId}/subscriptions` | Criar assinatura para um cliente |
| GET | `/clients/{clientId}/subscriptions` | Listar assinaturas de um cliente |

## 9. Exemplos de utilização

Os exemplos abaixo utilizam a URL base:

```text
http://localhost:8080
```

### 9.1 Cadastrar um cliente

**Requisição**

```http
POST /clients
Content-Type: application/json
```

**Exemplo de corpo JSON**

```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "SenhaSegura123!"
}
```

**Resposta esperada**

```http
HTTP/1.1 201 Created
```

O sistema retorna os dados do cliente criado e o cabeçalho `Location` com o endereço do novo recurso.

A senha não é retornada na resposta.

### 9.2 Consultar um cliente

**Requisição**

```http
GET /clients/1
```

**Exemplo de resposta**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@example.com"
}
```

### 9.3 Atualizar um cliente

**Requisição**

```http
PUT /clients/1
Content-Type: application/json
```

**Exemplo de corpo JSON**

```json
{
  "name": "João Silva Atualizado",
  "email": "joao.novo@example.com"
}
```

### 9.4 Criar uma assinatura

**Requisição**

```http
POST /subscriptions
Content-Type: application/json
```

**Exemplo de corpo JSON**

```json
{
  "name": "Netflix",
  "paymentDay": 10,
  "client": {
    "id": 1
  }
}
```

O exemplo representa uma assinatura vinculada ao cliente de ID 1. Os campos exatos devem seguir o modelo apresentado na documentação Swagger.

### 9.5 Criar uma assinatura para um cliente

**Requisição**

```http
POST /clients/1/subscriptions
Content-Type: application/json
```

**Exemplo de corpo JSON**

```json
{
  "name": "Spotify",
  "paymentDay": 15
}
```

Nesse endpoint, o ID do cliente é informado diretamente na URL.

O sistema retorna `201 Created` quando a assinatura é cadastrada com sucesso.

### 9.6 Listar assinaturas de um cliente

**Requisição**

```http
GET /clients/1/subscriptions
```

**Exemplo de resposta**

```json
[
  {
    "id": 1,
    "name": "Netflix",
    "paymentDay": 10
  },
  {
    "id": 2,
    "name": "Spotify",
    "paymentDay": 15
  }
]
```

### 9.7 Filtrar assinaturas pelo dia de pagamento

**Requisição**

```http
GET /subscriptions/paymentDay/10
```

Retorna as assinaturas cadastradas que possuem o dia de pagamento informado.

### 9.8 Excluir uma assinatura

**Requisição**

```http
DELETE /subscriptions/1
```

O sistema realiza a exclusão da assinatura identificada pelo ID.

## 10. Tratamento de erros

A aplicação possui tratamento centralizado de exceções para retornar respostas padronizadas.

Entre os principais cenários tratados estão:

| Código HTTP | Descrição |
|---|---|
| 400 Bad Request | Dados inválidos |
| 404 Not Found | Recurso não encontrado |
| 409 Conflict | Conflito com uma regra de negócio |
| 422 Unprocessable Entity | Violação de regra de negócio ou política de senha |

Os erros são tratados por meio da classe `RestExceptionHandler`.

## 11. Considerações finais

O Subscription APIs foi desenvolvido com foco na organização do código, boas práticas de desenvolvimento e utilização de tecnologias do ecossistema Java.

O projeto permite aplicar conceitos como:

- Desenvolvimento de APIs REST.
- Arquitetura em camadas.
- Persistência com Spring Data JPA.
- Mapeamento objeto-relacional com Hibernate.
- Validação de dados.
- Tratamento de exceções.
- Relacionamentos entre entidades.
- Containerização de banco de dados com Docker.
- Documentação de APIs com OpenAPI.

O projeto faz parte do portfólio de Engenharia de Software e poderá ser evoluído com novas funcionalidades, como notificações de vencimento, autenticação e autorização, testes automatizados e integração com serviços externos.
