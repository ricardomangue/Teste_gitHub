# Student CRUD (Spring Boot): base do laboratório de revisão de código com IA

Aplicação simples de gestão de estudantes (CRUD) com **Java 17, Spring Boot, Thymeleaf, JPA e H2**.
Serve de base para o laboratório **Revisão de código com IA (CodeRabbit + GitHub)**.

## Executar

```bash
mvn spring-boot:run
```

Abrir http://localhost:8080

## Estrutura

```
src/main/java/com/example/studentcrud/
├── controller/   # Endpoints web
├── model/        # Entidade Student
├── repository/   # Interface JPA
└── StudentCrudApplication.java
src/main/resources/
├── templates/    # Páginas Thymeleaf
└── application.properties
```

## Laboratório

Ver `LABORATORIO.md` para as etapas (boas práticas, optimização e refactoração, segurança).
A `main` contém a versão base. As pastas `etapas/` contêm os ficheiros a copiar em cada branch.

> Aviso: os ficheiros em `etapas/` contêm falhas **propositadas** para treino. Nunca fazer deploy deles.
