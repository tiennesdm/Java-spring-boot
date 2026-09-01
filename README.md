# Spring Boot Pilot — Task Tracker

Java Spring Boot seekhne ke liye chhota, complete REST project. Isme real app jaisi **layers** hain, H2 in-memory database hai, aur browser UI bhi hai.

## Ye project kya sikhata hai

| Concept | Is project me kahan |
| --- | --- |
| `@SpringBootApplication` + `main` | `SpringBootPilotApplication` |
| REST Controller | `task/TaskController` |
| Service (business logic) | `task/TaskService` |
| JPA Entity + Repository | `task/Task`, `task/TaskRepository` |
| DTO + Bean Validation | `task/TaskRequest` (`@NotBlank`, `@Size`) |
| Global errors | `common/GlobalExceptionHandler` |
| Auto demo data | `task/DemoDataLoader` |
| Static UI | `src/main/resources/static/index.html` |
| Tests | `src/test/java/.../TaskApiTest` |

Request ka flow:

```text
Browser / curl
    → TaskController   (HTTP)
    → TaskService      (rules)
    → TaskRepository   (SQL/JPA)
    → H2 database
```

## Requirements

- Java 21+
- Maven 3.8+ (ya bundled `./mvnw`)

## Run

```bash
./mvnw spring-boot:run
```

Phir open karo:

- UI: http://localhost:8080/
- API list: http://localhost:8080/api/tasks
- H2 console: http://localhost:8080/h2-console  
  JDBC URL: `jdbc:h2:mem:taskdb` · User: `sa` · Password: blank

Tests:

```bash
./mvnw test
```

## API cheatsheet

Create:

```bash
curl -s -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Learn @RestController","description":"Map HTTP to Java","status":"TODO"}'
```

List / filter:

```bash
curl -s http://localhost:8080/api/tasks
curl -s 'http://localhost:8080/api/tasks?status=TODO'
```

Get one, update, status, delete:

```bash
curl -s http://localhost:8080/api/tasks/1
curl -s -X PUT http://localhost:8080/api/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Learn @Service","status":"IN_PROGRESS"}'
curl -s -X PATCH http://localhost:8080/api/tasks/1/status \
  -H 'Content-Type: application/json' \
  -d '{"status":"DONE"}'
curl -s -o /dev/null -w '%{http_code}\n' -X DELETE http://localhost:8080/api/tasks/1
```

Valid `status` values: `TODO`, `IN_PROGRESS`, `DONE`.

## Seekhne ka order (suggested)

1. `pom.xml` — `spring-boot-starter-webmvc` HTTP deta hai, `data-jpa` + `h2` database.
2. `application.properties` — port, H2 URL, `ddl-auto=update`.
3. `Task` entity — table mapping.
4. `TaskRepository` — method name se query.
5. `TaskService` — create/update/delete + 404.
6. `TaskController` — GET/POST/PUT/PATCH/DELETE.
7. `TaskRequest` — kyun DTO, kyun `@Valid`.
8. `GlobalExceptionHandler` — error JSON ka shape.
9. `TaskApiTest` — MockMvc se API test.

## Agla practice (khud try karo)

- `dueDate` field entity + API me add karo
- `GET /api/tasks` pe title search (`?q=`)
- PostgreSQL switch (H2 ki jagah) — production jaisa next step

Stack: **Java 21**, **Spring Boot 4.0.8**, **Spring Web MVC**, **Spring Data JPA**, **H2**, **Bean Validation**.
