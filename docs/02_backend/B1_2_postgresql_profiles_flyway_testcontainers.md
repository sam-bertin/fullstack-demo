# Fiche de Tache Jira - B.1.2 Configurer PostgreSQL et profils

## 1) Identification

### Metadonnees
- **Cle Jira :** B.1.2
- **Epic :** EPIC B - Fondations Techniques
- **Fonctionnalite :** B.1 - Initialisation Backend Spring Boot
- **Tache :** Config PostgreSQL + profils Spring + migrations + tests integration realistes
- **Date :** 2026-04-15
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner lors du commit
- **Pull Request :** a renseigner

## 2) Contexte et objectif

### Probleme
Le backend ne demarre pas hors tests car aucune datasource n est configuree pour l execution locale.

### Justification
Sans strategie de persistence stable (profils, base locale, migration versionnee, tests integration sur PostgreSQL reel), les prochaines taches Auth/Chat sont bloquees.

### Valeur attendue
- Demarrage backend local fiable avec PostgreSQL.
- Schema versionne et reproductible.
- Tests integration alignes sur le moteur cible (PostgreSQL).

## 3) Perimetre

### Inclus
- Profils Spring et variables d environnement pour datasource.
- PostgreSQL local standardise via Docker Compose.
- Migrations Flyway (baseline initiale).
- Test integration Spring Boot avec Testcontainers PostgreSQL.
- Mise a jour de la documentation associee.

### Exclu
- Implementation des endpoints Auth.
- Architecture metier complete B.1.3.
- Hardening DB avance (roles least privilege SQL detailes, chiffrement applicatif avance).

## 4) Conception technique

### Solution retenue
- Configurer `application-dev` avec placeholders `${DB_URL}`, `${DB_USER}`, `${DB_PASSWORD}`.
- Activer un profil local par defaut pour simplifier le run dev.
- Ajouter Flyway pour les migrations SQL versionnees.
- Ajouter `docker-compose.yml` racine avec un service PostgreSQL.
- Ajouter Testcontainers pour valider une execution d integration sur PostgreSQL reel.

### Alternatives considerees
- H2 uniquement pour tous les tests (rejete: ecart dialecte SQL avec production).
- Liquibase (non retenu pour cette iteration: Flyway prefere pour simplicite SQL pure).
- PostgreSQL installe en local sans compose (rejete pour reproductibilite equipe).

### Justification du choix
Cette combinaison est la plus simple pour rendre l environnement reproductible rapidement, limiter les surprises SQL, et preparer les futures features metier.

### Compromis
- **Performance :** demarrage tests integration plus lent avec conteneur.
- **Securite :** secrets externalises, mais valeurs locales par defaut uniquement pour dev.
- **Complexite :** ajout de tooling (Flyway + Testcontainers + Compose).
- **Maintenance :** meilleure maitrise du schema dans le temps.

## 5) Implementation detaillee

### Fichiers modifies
- `backend/backend/pom.xml`
- `backend/backend/src/main/resources/application.properties`
- `backend/backend/src/main/resources/application-dev.properties` (nouveau)
- `backend/backend/src/test/resources/application-test.properties`
- `backend/backend/src/main/resources/db/migration/V1__init_schema.sql` (nouveau)
- `backend/backend/src/test/java/com/livechat/backend/BackendApplicationTests.java`
- `docker-compose.yml` (nouveau)
- `docs/00_onboarding/ONBOARDING.md`
- `docs/02_backend/BACKEND.md`
- `docs/04_devops/DEVOPS.md`
- `docs/STATUS.md`
- `docs/05_decisions/DECISIONS_LOG.md`

### Changement par fichier
- `backend/backend/pom.xml`
  - Ajout de `flyway-core` et `flyway-database-postgresql`.
  - Ajout d un BOM Testcontainers (`testcontainers-bom:2.0.4`) dans `dependencyManagement`.
  - Ajout de Testcontainers (`testcontainers-junit-jupiter`, `testcontainers-postgresql`) sans versions explicites.
  - Suppression de la dependance H2 pour aligner les tests integration sur PostgreSQL.
- `backend/backend/src/main/resources/application.properties`
  - Ajout de `spring.profiles.default=dev`.
- `backend/backend/src/main/resources/application-dev.properties`
  - Nouvelle configuration datasource dev via variables d environnement.
  - Activation Flyway et politique JPA `ddl-auto=validate`.
- `backend/backend/src/test/resources/application-test.properties`
  - Suppression datasource H2 en dur.
  - Configuration test orientee migration Flyway.
- `backend/backend/src/main/resources/db/migration/V1__init_schema.sql`
  - Baseline SQL versionnee (`users` + index email).
- `backend/backend/src/test/java/com/livechat/backend/BackendApplicationTests.java`
  - Conversion en test integration Testcontainers PostgreSQL.
  - Assertion explicite de presence de la table `users` creee par Flyway.
- `docker-compose.yml`
  - Ajout d une instance PostgreSQL locale standardisee avec healthcheck et volume.

### Mise a jour warning cleanup 2026-04-15
- Migration des tests integration backend de l usage `PostgreSQLContainer` vers un conteneur generique PostgreSQL explicite (`GenericContainer`) pour supprimer le warning deprecation IDE.
- URL JDBC test construite explicitement a partir de `host` et `mappedPort` pour conserver un comportement equivalent et transparent.

### Contrats impactes
- **API :** aucun endpoint ajoute.
- **Evenements :** aucun.
- **Base de donnees :** oui, baseline schema initiale versionnee.

## 6) Configuration et environnement

### Variables d environnement
- **Ajoutees/modifiees :** `DB_URL`, `DB_USER`, `DB_PASSWORD`.
- **Valeurs d exemple non sensibles :**
  - `DB_URL=jdbc:postgresql://localhost:5432/livechat`
  - `DB_USER=livechat`
  - `DB_PASSWORD=livechat`

### Prerequis
- Docker Desktop actif pour Compose/Testcontainers.
- Java 21 et Maven Wrapper operationnels.

## 7) Base de donnees

### Impacts schema
- **Entites/tables :** creation table `users` baseline.
- **Migrations :** Flyway `V1__init_schema.sql`.
- **Compatibilite :** schema explicitement versionne.

## 8) API et evenements

### API
- **Endpoints ajoutes/modifies :** aucun.
- **Payload request/response :** non applicable.
- **Codes d erreur et cas limites :** non applicable.

### Evenements
- **Flux d evenements (si applicable) :** non applicable.

## 9) Securite

### Analyse
- **Risques identifies :** secrets en dur, ecarts dev/prod, SQL non versionne.
- **Mesures appliquees :** variables d environnement, migration versionnee, tests integration reel DB.
- **Verifications effectuees :**
  - Backend demarre sur profil `dev` avec PostgreSQL local compose.
  - Flyway applique automatiquement la migration `V1`.
  - Test integration execute sur conteneur PostgreSQL 16 via Testcontainers.

## 10) Tests et validation

### Tests
- **Unitaires :** existants + context load.
- **Integration/E2E :** test integration PostgreSQL via Testcontainers.

### Resultats
- `LINT_OK` via `./mvnw.cmd checkstyle:check`.
- `TEST_OK` via `./mvnw.cmd test` (1 test Spring Boot/Testcontainers, 0 erreur).
- `PACKAGE_OK` via `./mvnw.cmd -DskipTests clean package`.
- Demarrage applicatif valide via `./mvnw.cmd -DskipTests spring-boot:run` avec profile `dev`.

### Verification manuelle
- `docker compose up -d postgres` -> conteneur `livechat-postgres` healthy.
- Demarrage backend -> datasource PostgreSQL connectee, migration Flyway appliquee, Tomcat up sur port 8080.
- Arret propre du process de verification apres confirmation de startup.

## 11) CI/CD et exploitation

### Impacts pipeline
- **Workflows impactes :** `ci-backend.yml` (tests backend executent le nouveau scope).
- **Build/deploiement impactes :** aucun deploiement; impact sur reproducibilite test.

### Exploitation
- **Logs/monitoring/runbooks impactes :** meilleure lisibilite des erreurs datasource/flyway.

## 12) Documentation mise a jour

### Documents modifies
- `docs/02_backend/B1_2_postgresql_profiles_flyway_testcontainers.md`
- `docs/00_onboarding/ONBOARDING.md`
- `docs/02_backend/BACKEND.md`
- `docs/04_devops/DEVOPS.md`
- `docs/STATUS.md`
- `docs/05_decisions/DECISIONS_LOG.md`

### Resume documentaire
- Strategie data backend executable localement, testable, et prete pour Auth.

## 13) Definition of Done

- **Code implemente et teste :** Oui
- **Documentation mise a jour :** Oui
- **Cle Jira referencee dans docs :** Oui
- **Onboarding possible sans oral :** Oui

## 14) Trace de decision

- **Reference Decision Log :** docs/05_decisions/DECISIONS_LOG.md
- **Action(s) de suivi :**
  - Enchainer B.1.3 pour couche metier verticale.
  - Enchainer C.1.1 pour entite User/repository/service auth.

## 15) Changelog tache

- **v1 :** Cadrage initial et perimetre technique valide.
- **v2 :** Implementation technique (profils, Flyway, compose, Testcontainers).
- **v3 :** Validation technique completee et documentation synchronisee.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** mise en place d une strategie data backend basee sur profils Spring, PostgreSQL local via Docker Compose, migrations Flyway et tests integration Testcontainers.
- **Impact principal :** demarrage backend stabilise et socle persistence fiable pour lancer l Auth.
- **Documentation associee :** docs/02_backend/B1_2_postgresql_profiles_flyway_testcontainers.md
- **Point d attention pour le prochain ticket :** lancer B.1.3 (architecture couches) puis C.1.1 (entite User et repository metier).