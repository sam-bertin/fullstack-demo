# Backend

## Stack
- Java 21, Spring Boot 3.x, Maven, PostgreSQL.

## Statut global
- **Implemented**
  - Socle Spring Boot initial.
  - CI backend lint/test/build.
  - Dependance WebSocket ajoutee (`spring-boot-starter-websocket`).
  - Convention package canonique retenue: `com.livechat.backend...`.
- **Planned**
  - Endpoints metier auth/chat.
  - Contrat API complet et gestion d'erreurs runtime.
  - Validation metier sur DTO request.

## Modules
- **Implemented (squelette B.1.3)**:
  - `com.livechat.backend.controller`
  - `com.livechat.backend.service`
  - `com.livechat.backend.serviceimpl`
  - `com.livechat.backend.repository`
  - `com.livechat.backend.dto`
  - `com.livechat.backend.config`
  - `com.livechat.backend.exception`
- **Planned**: implémenter les classes metier dans ces packages.

## Endpoints REST
- **Implemented**: aucun endpoint metier publie.
- **Planned**: lister endpoint, methode HTTP, payload request/response, erreurs possibles.

## Securite
- **Implemented**: dependance Spring Security et socle de projet.
- **Planned**:
  - JWT stateless.
  - BCrypt.
  - Segmentation endpoints publics/prives.

## Validation
- **Implemented**: dependance `spring-boot-starter-validation`.
- **Planned**: regles de validation DTO et comportements d'erreur associes.

## Persistence
- **Implemented**: dependance JPA + driver PostgreSQL.
- **Planned**: entites, relations, index, contraintes, politique de migration/schema.

## Tests
- **Implemented**: test de contexte et execution en CI backend.
- **Planned**: strategie de tests unitaires/integration, couverture cible et zones critiques.

## Trace Jira
- B.1.1, B.1.3, B.3.1, A.3.1, A.1.1, A.1.2, A.2.1, A.2.2.
