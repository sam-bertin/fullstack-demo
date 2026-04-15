# Backend

## Stack
- **[Implémente]** Java 21, Spring Boot, Maven, PostgreSQL driver.
- **[Implémente]** Flyway pour migration SQL versionnee.
- **[Implémente]** Testcontainers PostgreSQL pour tests integration backend.

## Modules
- **[Prevu]** Documenter chaque module backend et ses responsabilites.

## Endpoints REST
- **[Prevu]** Lister endpoint, methode HTTP, payload request/response, erreurs possibles.

## Securite
- **[Prevu]** JWT stateless.
- **[Prevu]** BCrypt.
- **[Prevu]** Endpoints publics/prives.

## Validation
- **[Prevu]** Regles de validation DTO.
- **[Prevu]** Comportements d'erreur associes.

## Persistence
- **[Partiellement Implemente]** Configuration datasource profilee (`dev`) via variables d environnement.
- **[Partiellement Implemente]** Baseline Flyway `V1__init_schema.sql` (table `users`, index email).
- **[Prevu]** Entites JPA metier, relations et contraintes finales.

## Tests
- **[Partiellement Implemente]** Test Spring Boot d integration sur PostgreSQL Testcontainers.
- **[Prevu]** Extension des tests service/repository/controllers avec couverture cible 80%+ couche service.

## Trace Jira
- Ticket(s): A.1.1, A.1.2, A.2.1, A.2.2, A.3.1, B.1.2.
