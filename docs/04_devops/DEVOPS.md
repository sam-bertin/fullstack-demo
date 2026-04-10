# DevOps

## CI/CD
- Workflow backend actif: `.github/workflows/ci-backend.yml`.
- Gates qualite backend: Checkstyle, tests JUnit 5, build Maven.
- Merge sur `main` bloque si les checks backend requis sont rouges.
- Workflow frontend a implementer quand le socle frontend existe.
- Execution backend CI containerisee: image `maven:3.9-eclipse-temurin-21`.
- Trigger backend CI: `push` uniquement, avec filtres sur les fichiers backend/workflow.
- Les commandes CI utilisent `mvn` dans le conteneur (equivalent local: `./mvnw.cmd ...` sous Windows).
- Cache Maven partage entre jobs: `/root/.m2/repository` via `actions/cache` pour limiter les retelechargements.

## Docker
- Dockerfile backend multi-stage.
- Dockerfile frontend multi-stage + nginx.
- Orchestration locale (compose).

## Workflow Git
- Branching model.
- Conventional commits.
- PR vers main uniquement.
- Templates PR separes: `.github/PULL_REQUEST_TEMPLATE/backend.md` et `.github/PULL_REQUEST_TEMPLATE/frontend.md`.

## Verification release locale
- Commandes exactes.
- Resultats attendus.

## Gouvernance outillage (obligatoire)
- Toute installation ou mise a jour d'outil impactant le build/test doit etre documentee au fil de l'eau.
- La source de verite pour l'installation locale est `docs/00_onboarding/ONBOARDING.md`.
- Chaque entree doit inclure:
	- Date
	- Cle Jira associee
	- Outil/version
	- Chemin binaire (pattern portable, sans chemin personnel complet)
	- Commandes de verification
	- Resultat attendu et resultat observe

### Commandes minimales de verification outillage backend
- `java -version` -> doit retourner Java 21.x
- `javac -version` -> doit retourner Java 21.x
- `mvn -v` -> doit retourner Maven 3.9+ et Java 21.x
- `where.exe java`
- `where.exe javac`
- `where.exe mvn`

### Commandes minimales de verification CI backend
- `cd backend/backend`
- `.\mvnw.cmd checkstyle:check`
- `.\mvnw.cmd test`
- `.\mvnw.cmd -DskipTests clean package`
- Attendu: `LINT_OK`, `TEST_OK`, `PACKAGE_OK`

### Branch protection backend
- `branch-ruleset.json` exige pour `main`:
	- `CI Backend / Lint (Checkstyle)`
	- `CI Backend / Unit Tests (JUnit 5)`
	- `CI Backend / Build (Maven)`

## Trace Jira
- Ticket(s): a renseigner.



