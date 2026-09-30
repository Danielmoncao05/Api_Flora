# 🌱 Flora

Sistema de gerenciamento de plantas residenciais desenvolvido com o objetivo de auxiliar no cuidado e monitoramento de plantas e hortas por meio da integração com sensores IoT.

## 📌 Sobre o projeto

O **Flora** é uma aplicação desenvolvida para auxiliar pessoas que possuem dificuldades no cuidado de plantas e hortas cultivadas em suas residências.

O sistema permite cadastrar plantas, ambientes e sensores responsáveis pela coleta de informações do cultivo. A partir dos dados obtidos pelos sensores, a aplicação pode auxiliar na identificação das necessidades de cada planta e indicar quando determinados cuidados devem ser realizados.

O projeto foi desenvolvido como Trabalho de Conclusão de Curso (TCC) na Instituição senai junto com minha equipe, envolvendo conceitos de desenvolvimento de APIs, banco de dados, autenticação e integração com IoT.

## 🚀 Funcionalidades

- 👤 Cadastro de usuários
- 🏠 Cadastro de ambientes
- 🌱 Cadastro de plantas
- 📡 Cadastro de sensores
- 🔐 Autenticação de usuários utilizando JWT
- ✅ Validação de dados
- ⚠️ Tratamento de erros e exceções
- 📊 Gerenciamento das informações relacionadas ao cultivo

## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Spring Security**
- **JWT**
- **Maven**
- **API REST**

## 🧠 Conceitos aplicados

Durante o desenvolvimento do projeto foram aplicados conceitos como:

- API REST
- CRUD
- Programação Orientada a Objetos (POO)
- DTOs (Data Transfer Objects)
- Entidades e relacionamentos
- JPA / Hibernate
- Validação de dados
- Tratamento de erros e exceções
- Autenticação e autorização com JWT
- Separação de responsabilidades
- Arquitetura em camadas

## 🗂️ Estrutura do Projeto

O projeto foi organizado buscando separar as responsabilidades entre as camadas de **aplicação, domínio, infraestrutura e interface**, facilitando a manutenção, evolução e organização do código.

```
src/
├── main/
│   ├── java/
│   │   └── com.flora/
│   │       │
│   │       ├── application/
│   │       │   ├── dto/
│   │       │   │   ├── ambiente/
│   │       │   │   ├── auth/
│   │       │   │   ├── sensor/
│   │       │   │   └── usuario/
│   │       │   │
│   │       │   └── service/
│   │       │       ├── ambiente/
│   │       │       ├── arquivo/
│   │       │       ├── auth/
│   │       │       └── usuario/
│   │       │
│   │       ├── domain/
│   │       │   ├── entity/
│   │       │   ├── enums/
│   │       │   ├── exception/
│   │       │   └── repository/
│   │       │
│   │       ├── infrastructure/
│   │       │   ├── config/
│   │       │   │   ├── bootstrap/
│   │       │   │   ├── storage/
│   │       │   │   └── swagger/
│   │       │   │
│   │       │   └── security/
│   │       │       ├── config/
│   │       │       ├── jwt_auth/
│   │       │       └── service/
│   │       │
│   │       ├── interface_ui/
│   │       │   ├── controller/
│   │       │   │   ├── ambiente/
│   │       │   │   ├── auth/
│   │       │   │   └── usuario/
│   │       │   │
│   │       │   └── handler/
│   │       │
│   │       └── FloraSaadApplication.java
│   │
│   └── resources/
│       ├── mysql/
│       ├── postman/
│       ├── swagger/
│       └── application.properties
│
└── test/
    └── java/
        └── com.flora/
            └── unit/
                └── ambiente/
                    └── AmbienteUnitTest.java

.gitignore
.gitignore
mvnw
mvnw.cmd
pom.xml
```

### 📦 Organização das camadas

#### `application`

Contém elementos relacionados à execução dos casos de uso da aplicação.

- **`dto`** — objetos utilizados para transferência de dados entre as diferentes partes da aplicação.
- **`service`** — concentra os serviços e regras relacionadas aos casos de uso.

#### `domain`

Representa o núcleo do domínio da aplicação.

- **`entity`** — entidades utilizadas na modelagem do sistema.
- **`enums`** — enumerações utilizadas pelo domínio.
- **`exception`** — exceções específicas relacionadas às regras da aplicação.
- **`repository`** — interfaces responsáveis pelo acesso e persistência dos dados.

#### `infrastructure`

Contém componentes relacionados à infraestrutura necessária para o funcionamento da aplicação.

- **`config`** — configurações gerais da aplicação.
- **`bootstrap`** — componentes utilizados na inicialização e configuração inicial do sistema.
- **`storage`** — componentes relacionados ao armazenamento.
- **`swagger`** — configuração da documentação da API.
- **`security`** — componentes relacionados à segurança e autenticação.
- **`jwt_auth`** — componentes responsáveis pelo processo de autenticação utilizando JWT.

#### `interface_ui`

Responsável pela comunicação da aplicação com o ambiente externo.

- **`controller`** — endpoints responsáveis por receber e responder às requisições HTTP.
- **`handler`** — tratamento centralizado de exceções e respostas de erro da API.

#### `resources`

Contém arquivos auxiliares e configurações necessárias para execução da aplicação.

- **`application.properties`** — configurações da aplicação e conexão com o banco de dados.
- **`mysql`** — arquivos relacionados ao banco de dados.
- **`postman`** — recursos utilizados para testes das requisições da API.
- **`swagger`** — recursos relacionados à documentação da API.

#### `test`

Contém os testes automatizados do projeto, organizados de acordo com suas respectivas funcionalidades.

## 🏛️ Visão simplificada da arquitetura

```
                    ┌──────────────────────┐
                    │      Cliente         │
                    │ Front-end / Postman  │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    interface_ui      │
                    │ Controllers/Handlers │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     application      │
                    │   DTOs / Services    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │        domain        │
                    │ Entities / Repository│
                    │ Exceptions / Enums   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   infrastructure     │
                    │ Security / Storage   │
                    │ Config / Swagger     │
                    └──────────┬───────────┘
                               │
                               ▼
                         ┌───────────┐
                         │   MySQL   │
                         └───────────┘
```

### Controller

Responsável por receber as requisições HTTP e disponibilizar os endpoints da API.

### Service

Responsável pelas regras de negócio e pelo processamento das operações realizadas pela aplicação.

### Domain

Contém as principais entidades e regras relacionadas ao domínio da aplicação.

### Infrastructure

Contém componentes relacionados à infraestrutura do sistema, como configurações, persistência e outros recursos necessários para o funcionamento da aplicação.

## 🔐 Autenticação

A aplicação utiliza **JWT (JSON Web Token)** para realizar a autenticação dos usuários.

O fluxo básico é:

```
Usuário
   │
   ▼
Login
   │
   ▼
API
   │
   ▼
Validação das credenciais
   │
   ▼
JWT
   │
   ▼
Acesso aos endpoints protegidos
```

## ▶️ Como executar o projeto

### Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 21
- Maven
- MySQL

### Clonando o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

Entre na pasta do projeto:

```bash
cd seu-repositorio
```

Configure as informações de conexão com o banco de dados no arquivo de configuração da aplicação.

Em seguida, execute:

```bash
mvn spring-boot:run
```

## 🗄️ Banco de dados

O projeto utiliza **MySQL** para armazenamento e gerenciamento dos dados da aplicação.

A persistência dos dados é realizada utilizando **Spring Data JPA** e **Hibernate**, permitindo o mapeamento das entidades Java para as tabelas do banco de dados.

## 📚 Aprendizados

O desenvolvimento do Flora proporcionou a aplicação prática de conhecimentos relacionados ao desenvolvimento de APIs utilizando Java e Spring Boot.

Entre os principais aprendizados estão:

- Desenvolvimento de APIs REST;
- Organização de uma aplicação em camadas;
- Utilização de DTOs;
- Modelagem e relacionamento entre entidades;
- Implementação de validações;
- Tratamento de exceções;
- Implementação de autenticação utilizando JWT;
- Integração entre diferentes partes de um sistema;
- Desenvolvimento de uma solução envolvendo IoT.

## 🔮 Possíveis melhorias

- [ ]  Implementar dashboard para acompanhamento das plantas
- [ ]  Adicionar histórico das medições dos sensores
- [ ]  Implementar notificações automáticas

## 👨‍💻 Autor

**Daniel Monção , Pedro Quidute, Lucas Emanuel, Giovanna Queiróz, Evandro e Leandro**

Estudante de Análise e Desenvolvimento de Sistemas na instituição Senai Anchieta, com foco em desenvolvimento Back-end utilizando Java e Spring Boot.
