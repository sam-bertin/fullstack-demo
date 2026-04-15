# Backend

## Stack
- **[Implémente]** Java 21, Spring Boot, Maven, PostgreSQL driver.
- **[Implémente]** Flyway pour migration SQL versionnee.
- **[Implémente]** Testcontainers PostgreSQL pour tests integration backend.

## Modules
- **[Partiellement Implemente]** `common` (ApiResponse, exceptions), `config` (security), `user` (entity/repository/service), `auth` (dto/controller/service).
- **[Prevu]** Etendre avec modules chat et token JWT (sprint suivant).

## Endpoints REST
- **[Implémente]** `POST /api/v1/auth/register`.
- **[Implémente]** `POST /api/v1/auth/login`.
- **[Implémente]** Reponse standard `ApiResponse<T>` + mapping erreur global.

## Securite
- **[Partiellement Implemente]** BCrypt pour hash mots de passe.
- **[Partiellement Implemente]** SecurityFilterChain transitoire sans JWT: endpoints auth publics, reste ferme par defaut.
- **[Prevu]** JWT stateless (access/refresh) et securisation complete C.1.4.

## Validation
- **[Implémente]** Validation DTO backend (`@Valid`, `@Email`, `@Size`, `@NotBlank`) pour register/login.
- **[Implémente]** Erreurs standardisees (`VALIDATION_ERROR`, `INVALID_CREDENTIALS`, `RESOURCE_CONFLICT`).

## Persistence
- **[Partiellement Implemente]** Configuration datasource profilee (`dev`) via variables d environnement.
- **[Partiellement Implemente]** Baseline Flyway `V1__init_schema.sql` (table `users`, index email).
- **[Prevu]** Entites JPA metier, relations et contraintes finales.

## Tests
- **[Implémente]** Tests integration PostgreSQL Testcontainers pour auth controller.
- **[Implémente]** Tests unitaires service auth (`AuthServiceImplTest`).
- **[Prevu]** Etendre la couverture des services metier suivants.

## Trace Jira
- Ticket(s): A.1.1, A.1.2, A.2.1, A.2.2, A.3.1, B.1.2, B.1.3, C.1.1, C.1.2, C.1.3.
