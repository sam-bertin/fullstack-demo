# DevOps

## CI/CD
- Workflow backend actif: `.github/workflows/ci-backend.yml`.
- Workflow frontend actif: `.github/workflows/ci-frontend.yml`.
- Gates qualite backend: Checkstyle, tests JUnit 5, build Maven.
- Gates qualite frontend: ESLint, tests Vitest, build Vite.
- Merge sur `main` bloque si les checks backend requis sont rouges.
- Trigger backend CI: `push` uniquement, avec filtres sur les fichiers backend/workflow.
- Trigger frontend CI: `push` uniquement, avec filtres sur les fichiers frontend/workflow.
- Execution backend CI sur runner natif `ubuntu-latest` avec `./mvnw`.
- Execution frontend CI sur runner natif `ubuntu-latest` avec `npm ci`.
- Le wrapper Maven du depot porte la version Maven, `setup-java` ne gere que le JDK 21 et le cache Maven.
- Cache Maven gere par `actions/setup-java@v4` avec `cache: 'maven'` dans chaque job.
- Cache npm frontend gere par `actions/setup-node@v4` avec `cache: 'npm'` et `cache-dependency-path: frontend/package-lock.json`.

## Docker
- Orchestration locale PostgreSQL implantee via `docker-compose.yml` (B.1.2).
- Dockerfile backend multi-stage: prevu.
- Dockerfile frontend multi-stage + nginx: prevu.

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

### Commandes minimales de verification B.1.2 (data strategy)
- `docker compose up -d postgres`
- `cd backend/backend`
- `.\mvnw.cmd test`
- `.\mvnw.cmd -DskipTests spring-boot:run`
- Attendu: migration Flyway appliquee, datasource PostgreSQL connectee, backend demarre.

### Commandes minimales de verification CI frontend
- `cd frontend`
- `npm ci`
- `npm run lint`
- `npm run test`
- `npm run build`
- Attendu: `LINT_OK`, `TEST_OK`, `BUILD_OK`

### Branch protection backend
- `branch-ruleset.json` exige pour `main`:
	- `CI Backend / Lint (Checkstyle)`
	- `CI Backend / Unit Tests (JUnit 5)`
	- `CI Backend / Build (Maven)`

### Branch protection frontend
- Les checks frontend sont maintenant disponibles via le workflow CI Frontend.
- Leur activation comme checks requis de merge est prevue dans B.3.3.

## Trace Jira
- Ticket(s): a renseigner.



