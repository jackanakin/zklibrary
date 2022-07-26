## Desafio: Biblioteca Interact
Desenvolver uma aplicação Java Web com acesso a um banco de dados, realizando inserções e consultas

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

### Diagrama ER
![alt text](https://github.com/jackanakin/zklibrary/blob/main/er_db.png?raw=true)
