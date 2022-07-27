## Desafio: Biblioteca Interact
Desenvolver uma aplicação Java Web com acesso a um banco de dados, realizando inserções e consultas

### Resultado (Live Demo)
https://zklibrary.kuhn.dev.br/
<br/>sysadmin/fun123


### Features
- [x] CRUD de livros (campos obrigatórios: código e nome);
- [x] CRUD de pessoas (campos obrigatórios: nome, senha, e email);
- [x] Exigir login ao entrar no sistema;
- [x] Permitir que o usuário retire o livro (existe apenas uma cópia de cada livro);
- [x] Listar as minhas reservas dos livros e os livros disponíveis,
- [x] Além do cadastro de livros, o sistema deve consultar e reservar os livros disponibilizados através api rest: https://sa.interact.com.br/sa/rs/books
- [x] Habilitar uma REST api para consulta delas, não é necessário autenticação

| Tipo | url | valor |
|------|-----|-------|
| GET  | http://localhost:8080/rs/books/ | |
| GET  | http://localhost:8080/rs/books/{id} | id = código do livro |

- [x] Utilizar o Framework ZK
- [ ] Permitir filtros nas listagens

### Stack/Frameworks
* Java
* ZK Framework
* Spring Boot
* Spring Security
* Spring JPA
* Flyway
* PostgreSQL
* Maven/Git
* Docker

### Diagrama ER
![alt text](https://github.com/jackanakin/zklibrary/blob/main/er_db.png?raw=true)

### Deploy/Build
Escolha um dos métodos abaixo para fazer o deploy<br/>
<br/>
:warning:	O banco de dados é gerenciado pelo flyway, não é necessário executar '.sql' manualmente<br/>

#### 1. Docker compose:
> docker compose up -d

Pronto, agora basta aguardar 15 segundos e acessar http://localhost:8080, já deixei o usuário sysadmin/fun123 memorizado na tela de login

#### 2. Docker c/ containers avulsos: 

Execute os comandos abaixo para levantar uma 'network' + 'postgres' + 'app', você pode manter os parâmetros como estão para um deploy de teste
> docker network create zklibrarynetwork

> docker run -d --net zklibrarynetwork --name zklibrarydb -e POSTGRES_USER=libraryowner -e POSTGRES_DB=zklibrary -e POSTGRES_PASSWORD=libraryownerpasswd postgres:14.4

Aguarde 15 segundos para o banco de dados começar a aceitar conexões, altere a porta 8080 se deseja utilizar outra para a aplicaçãop
> docker run -d --net zklibrarynetwork -p 8080:8080 --name zklibraryapp jackanakin/zklibrary --datasource_url=zklibrarydb:5432/zklibrary

Pronto, agora basta acessar http://localhost:8080, já deixei o usuário sysadmin/fun123 memorizado na tela de login

#### 3. Build manual: 
:warning: Se fizer o deploy localmente usar a versão de JAVA 'openjdk:8-jdk'<br/>
>Renomeie o arquivo 'src/main/resources/application.properties.dev' para 'src/main/resources/application.properties'

Altere apenas os parâmetros de conexão com o 'PostgreSQL' e 'Flyway', também pode deixar como está e apenas seguir os comandos abaixo

Execute os comandos abaixo na raíz:

> ./mvnw clean install

Build construída, antes de rodar o comando seguinte é preciso ter um banco de dados 'PostgreSQL', você pode subir com Docker:
> docker run -d --name librarydb -e POSTGRES_USER=libraryowner -e POSTGRES_DB=zklibrary -e POSTGRES_PASSWORD=libraryownerpasswd -p 5432:5432 postgres:14.4

E para iniciar a aplicação:
> ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8080

Acesse http://localhost:8080 ou com a porta customizada, utilize o usuário sysadmin/fun123 memorizado na tela de login
