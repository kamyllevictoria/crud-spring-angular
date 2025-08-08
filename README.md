# CRUD Full-stack com Angular e Spring Boot

Este projeto é uma aplicação **full-stack** completa, desenvolvida para demonstrar a criação de um **CRUD (Create, Read, Update, Delete)** de cursos. A solução é composta por um **frontend** em Angular e um **backend** em Spring Boot, com a aplicação inteira conteinerizada usando Docker para garantir um ambiente de desenvolvimento consistente e portátil.

---

### 🚀 Stacks Principais

| Componente | Tecnologia | Detalhes Principais |
| :--- | :--- | :--- |
| **Frontend** | **Angular** | SPA (Single Page Application) com **Angular Material** para a UI, **RxJS** para gerenciamento de estado assíncrono e arquitetura de componentes **Smart/Dumb**. |
| **Backend** | **Spring Boot** | API RESTful com **Spring Data JPA**, banco de dados **MySQL** e um modelo de persistência com **soft delete**. |
| **Containerização** | **Docker & Docker Compose** | Orquestração dos serviços (backend e banco de dados) em contêineres, garantindo um ambiente de desenvolvimento e produção robusto e consistente. |

---

### 💻 Frontend (Angular)

O frontend é a camada de apresentação, construída com o framework **Angular**. Ele é responsável por toda a interface do usuário e pela comunicação com o backend.

#### Estrutura de Componentes

A arquitetura de componentes segue o padrão **Smart/Dumb**:
* **`CoursesComponent` (Smart Component)**: Atua como um contêiner, gerenciando a lógica de negócio, a paginação, a busca de dados e o estado da aplicação.
* **`CoursesListComponent` (Dumb Component)**: É um componente de apresentação. Recebe dados via `@Input()`, exibe-os em uma tabela usando **Angular Material** e emite eventos via `@Output()` para interações do usuário.

#### Gerenciamento de Dados

* Utilizamos **Observables do RxJS** para lidar com fluxos de dados assíncronos.
* O **`async pipe`** é usado para desempacotar esses Observables de forma eficiente no template.
* O **Angular Material** fornece componentes como **`MatTable`** e **`MatPaginator`** para uma UI responsiva e completa, incluindo paginação e navegação.

---

### ⚙️ Backend (Spring Boot)

O backend é uma **API RESTful** construída com **Spring Boot** e Java. Ele gerencia a lógica de negócio, a persistência de dados e a comunicação com o banco de dados.

#### Arquitetura de Camadas

A arquitetura é dividida em três camadas claras:
* **Controllers (`CourseController`)**: Recebem as requisições HTTP e delegam a lógica para a camada de serviço.
* **Services (`CourseService`)**: Contêm a lógica de negócio principal.
* **Repositories (`CourseRepository`)**: Interfaces que utilizam o **Spring Data JPA** para abstrair a interação com o banco de dados **MySQL**.

#### Persistência e Paginação

* O **Spring Data JPA** mapeia objetos Java para tabelas no banco de dados.
* A entidade **`Course`** implementa o conceito de **soft delete** usando as anotações `@SQLDelete` e `@Where`, permitindo que os dados sejam marcados como inativos em vez de serem excluídos permanentemente.
* A paginação é gerenciada no backend, garantindo que as páginas de dados sejam retornadas de forma consistente e ordenada.

---

### 📦 Containerização com Docker

A aplicação completa é conteinerizada, garantindo que ela funcione da mesma forma em qualquer ambiente.

* **`Dockerfile`**: Define como a imagem Docker do backend é construída.
* **`docker-compose.yml`**: Orquestra os serviços da aplicação, incluindo o contêiner do **MySQL** e o do backend (**`crud-spring`**). Uma rede e um volume são configurados para garantir a comunicação entre os serviços e a persistência dos dados do banco.
