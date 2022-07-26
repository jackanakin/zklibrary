## Desafio: Biblioteca Interact
Desenvolver uma aplicação Java Web com acesso a um banco de dados, realizando inserções e consultas

### Features
- [x] CRUD de livros (campos obrigatórios: código e nome);
- [x] CRUD de pessoas (campos obrigatórios: nome, senha, e email);
- [x] Exigir login ao entrar no sistema;
- [x] Permitir que o usuário retire o livro (existe apenas uma cópia de cada livro);
- [x] Listar as minhas reservas dos livros e os livros disponíveis,
- [x] Além do cadastro de livros, o sistema deve consultar e reservar os livros disponibilizados através api rest: https://sa.interact.com.br/sa/rs/books
- [ ] Permitir filtros nas listagens
- [x] Habilitar uma REST api para consulta delas, não é necessário autenticação
- [x] Utilizar o Framework ZK

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
:warning:	O banco de dados é gerenciado pelo flyway, não é necessário executar '.sql' manualmente

#### 1. Docker: 

Execute os comandos abaixo para levantar uma 'network' + 'postgres' + 'app', você pode manter os parâmetros como estão para um deploy de teste
> docker network create library_network

> docker run -d -it --net library_network --name library_db -e POSTGRES_USER=libraryowner -e POSTGRES_DB=zklibrary -e POSTGRES_PASSWORD=libraryownerpasswd postgres

Altera a porta 8080 se deseja utilizar outra
> docker run -d -it --net library_network -p 8080:8080 --name spring jackanakin/zklibrary --datasource_url=library_db:5432/zklibrary

Pronto, agora basta acessar http://localhost:8080, já deixei o usuário sysadmin/fun123 memorizado na tela de login

#### 2. Build manual: 
Renomeie o arquivo 'src/main/resources/application.properties.dev' para 'src/main/resources/application.properties'<br/>
Altere apenas os parâmetros de conexão com o 'PostgreSQL' e 'Flyway', também pode deixar como está e apenas seguir os comandos abaixo

Execute os comandos abaixo na raíz:

> ./mvnw clean install

Build executada, antes de rodar o próximo comando é preciso ter um banco de dados 'PostgreSQL', você pode com Docker:
> docker run -d --name library_db -e POSTGRES_USER=libraryowner -e POSTGRES_DB=zklibrary -e POSTGRES_PASSWORD=libraryownerpasswd -p 5432:5432 postgres

E para iniciar a aplicação:
> java -jar target/library-0.0.1-SNAPSHOT.jar --server.port=8080
