# Fiche de Tache Jira - B.1.3 + C.1.1/C.1.2/C.1.3 (Sprint socle auth sans JWT)

## 1) Identification

### Metadonnees
- **Cle Jira :** B.1.3 + C.1.1 + C.1.2 + C.1.3
- **Epic :** EPIC B / EPIC C
- **Fonctionnalite :** Architecture en couches + Auth backend
- **Tache :** Implémenter un socle backend réutilisable avec register/login sans JWT (JWT reporté)
- **Date :** 2026-04-15
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner
- **Pull Request :** a renseigner

## 2) Contexte et objectif

### Probleme
Le backend dispose de la couche data (B.1.2) mais pas encore de structure applicative réutilisable ni de flux auth utilisable pour valider le socle MVP.

### Justification
Avant d'ajouter des fonctionnalités métier complexes, il faut établir une architecture claire testable et sécurisée, puis valider le socle avec un cas fonctionnel minimal: register/login.

### Valeur attendue
- Architecture backend propre et maintenable.
- Flux register/login opérationnel avec hash BCrypt.
- Réponses API standardisées et gestion d'erreurs centralisée.

## 3) Perimetre

### Inclus
- Architecture en couches minimale: controller, service, serviceimpl, repository, dto, config, exception.
- Modèle `User`, repository, contraintes unicité.
- Endpoint register (hash BCrypt).
- Endpoint login (validation identifiants, sans JWT).
- Sécurisation transitoire sans JWT (endpoints auth publics).

### Exclu
- Génération/validation JWT (reportée sprint suivant).
- Refresh token.
- Gestion de rôles avancée.

## 4) Conception technique

### Solution retenue
- `UserEntity` persistée PostgreSQL.
- Services dédiés `UserService` et `AuthService`.
- Contrat de réponse `ApiResponse<T>`.
- `@RestControllerAdvice` global pour mapper les erreurs.
- `SecurityFilterChain` transitoire: auth publique, reste fermé par défaut.

### Alternatives considerees
- Auth complète JWT immédiate.
- Implémentation directe en controller sans services.

### Justification du choix
Le choix réduit le risque de couplage et permet de livrer rapidement un socle exploitable sans bloquer sur JWT.

### Compromis
- **Performance :** impact négligeable.
- **Securite :** baseline correcte (BCrypt) mais pas encore stateless JWT.
- **Complexite :** un peu plus de structure immédiate pour réduire la dette future.
- **Maintenance :** meilleure séparation des responsabilités.

## 5) Implementation detaillee

### Fichiers modifies
- `backend/backend/src/main/java/com/livechat/backend/common/dto/ApiResponse.java`
- `backend/backend/src/main/java/com/livechat/backend/common/exception/ErrorCode.java`
- `backend/backend/src/main/java/com/livechat/backend/common/exception/AppException.java`
- `backend/backend/src/main/java/com/livechat/backend/common/exception/GlobalExceptionHandler.java`
- `backend/backend/src/main/java/com/livechat/backend/config/SecurityConfig.java`
- `backend/backend/src/main/java/com/livechat/backend/user/entity/UserEntity.java`
- `backend/backend/src/main/java/com/livechat/backend/user/repository/UserRepository.java`
- `backend/backend/src/main/java/com/livechat/backend/user/service/UserService.java`
- `backend/backend/src/main/java/com/livechat/backend/user/serviceimpl/UserServiceImpl.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/dto/RegisterRequest.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/dto/LoginRequest.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/dto/AuthUserResponse.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/dto/LoginResponse.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/service/AuthService.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/serviceimpl/AuthServiceImpl.java`
- `backend/backend/src/main/java/com/livechat/backend/auth/controller/AuthController.java`
- `backend/backend/src/main/resources/application-dev.properties`
- `backend/backend/src/test/java/com/livechat/backend/auth/serviceimpl/AuthServiceImplTest.java`
- `backend/backend/src/test/java/com/livechat/backend/auth/controller/AuthControllerIntegrationTest.java`

### Changement par fichier
- `ApiResponse` et `GlobalExceptionHandler` ajoutent un contrat de réponse stable et un mapping d'erreurs global.
- `SecurityConfig` met en place une sécurité transitoire sans JWT: auth publique, reste fermé, CORS local configuré.
- `UserEntity`/`UserRepository`/`UserService` posent la couche persistence/service en séparation claire.
- `AuthServiceImpl` implémente register/login avec hash BCrypt et gestion des erreurs métier.
- `AuthController` expose les endpoints REST `/api/v1/auth/register` et `/api/v1/auth/login`.
- `AuthServiceImplTest` couvre les cas critiques register/login (succès + erreurs).
- `AuthControllerIntegrationTest` valide le flux register/login complet sur PostgreSQL Testcontainers.

### Mise a jour review 2026-04-15
- Ajout d'un logging serveur explicite dans `GlobalExceptionHandler` pour les exceptions inattendues (stacktrace conservée côté logs, message générique conservé côté client).
- Ajout d'un mapping `DataIntegrityViolationException` vers `409 RESOURCE_CONFLICT` pour éviter les retours 500 opaques sur conflit de contrainte.
- Vérification explicite de l'unicité `username` avant persistance dans `AuthServiceImpl` (409 + `RESOURCE_CONFLICT`).
- Extension de la couche user (`UserRepository`/`UserService`) pour supporter le contrôle `existsByUsername`.
- Extension des tests unitaires/intégration pour couvrir le scénario `duplicate username`.

### Mise a jour warning cleanup 2026-04-15
- Ajout du metadata Spring `backend/backend/src/main/resources/META-INF/additional-spring-configuration-metadata.json` pour enregistrer `app.cors.allowed-origins` et supprimer l alerte de propriete inconnue dans l IDE.
- Nettoyage de warning nullability sur `UserServiceImpl.save` avec garde runtime (`Objects.requireNonNull`) conservee et suppression locale du warning pour le contrat JPA.

### Contrats impactes
- **API :** Ajout endpoints register/login.
- **Evenements :** non applicable.
- **Base de donnees :** utilisation de la table `users` existante.

## 6) Configuration et environnement

### Variables d'environnement
- **Ajoutees/modifiees :** `APP_CORS_ALLOWED_ORIGINS` (optionnelle).
- **Valeurs d'exemple non sensibles :** `APP_CORS_ALLOWED_ORIGINS=http://localhost:5173`.

### Prerequis
- B.1.2 validé (PostgreSQL/Flyway/Testcontainers).

## 7) Base de donnees

### Impacts schema
- **Entites/tables :** exploitation table `users`.
- **Migrations :** pas de nouvelle migration prévue dans ce sprint.
- **Compatibilite :** compatible avec baseline Flyway existante.

## 8) API et evenements

### API
- **Endpoints ajoutes/modifies :** `/api/v1/auth/register`, `/api/v1/auth/login`.
- **Payload request/response :** DTOs Request/Response dédiés.
- **Codes d'erreur et cas limites :** validation, conflit email, conflit username, identifiants invalides.

### Evenements
- **Flux d'evenements (si applicable) :** non applicable.

## 9) Securite

### Analyse
- **Risques identifies :** stockage de mot de passe, exposition endpoints.
- **Mesures appliquees :** BCrypt + sécurité transitoire + erreurs standardisées.
- **Verifications effectuees :** a compléter.

## 10) Tests et validation

### Tests
- **Unitaires :** services auth/user.
- **Integration/E2E :** tests endpoints auth.

### Resultats
- `mvn test`: OK (10 tests, 0 erreur, 0 échec).
- Register/login validés via test d'intégration `AuthControllerIntegrationTest` sur PostgreSQL Testcontainers.

### Verification manuelle
- Backend démarre en profil `dev` et expose les endpoints auth.
- `register` crée un utilisateur avec mot de passe hashé.
- `login` valide les identifiants et renvoie un mode transitoire `PASSWORD_ONLY_TEMPORARY`.

## 11) CI/CD et exploitation

### Impacts pipeline
- **Workflows impactes :** ci-backend.yml.
- **Build/deploiement impactes :** aucun changement de pipeline attendu.

### Exploitation
- **Logs/monitoring/runbooks impactes :** a compléter si nécessaire.

## 12) Documentation mise a jour

### Documents modifies
- Ce document + hubs backend/devops/status.

### Resume documentaire
- Socle backend auth sans JWT implémenté pour validation MVP.

## 13) Definition of Done

- **Code implemente et teste :** Oui
- **Documentation mise a jour :** Oui
- **Cle Jira referencee dans docs :** Oui
- **Onboarding possible sans oral :** Oui

## 14) Trace de decision

- **Reference Decision Log :** docs/05_decisions/DECISIONS_LOG.md
- **Action(s) de suivi :** implémenter JWT sprint suivant.

## 15) Changelog tache

- **v1 :** Cadrage initial avant implémentation.
- **v2 :** Implémentation architecture backend minimale + register/login sans JWT.
- **v3 :** Validation automatique (tests) et finalisation documentaire.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** lancement d'un sprint socle auth sans JWT pour maximiser maintenabilité/testabilité avant la couche token.
- **Impact principal :** architecture backend réutilisable + register/login opérationnels.
- **Documentation associee :** docs/02_backend/B1_3_C1_auth_foundation_without_jwt.md.
- **Point d'attention pour le prochain ticket :** C.1.4 JWT + sécurisation stateless complète.