# DevOps

## CI/CD
- Workflow backend actif: `.github/workflows/ci-backend.yml`.
- Workflow frontend actif: `.github/workflows/ci-frontend.yml`.
- Workflow QA actif: `.github/workflows/ci-qa.yml`.
- Gates qualite backend: Checkstyle, tests JUnit 5, build Maven.
- Gates qualite frontend: ESLint, tests Vitest, build Vite.
- Gates qualite QA: API smoke Bruno, E2E Playwright.
- Merge sur `main` bloque si les checks backend requis sont rouges (etat actuel de `branch-ruleset.json`).
- Checks frontend et QA disponibles en CI, mais encore a activer en required status checks pour bloquer le merge.
- Trigger backend CI: `push` + `pull_request`, avec filtres sur les fichiers backend/workflow.
- Trigger frontend CI: `push` + `pull_request`, avec filtres sur les fichiers frontend/workflow.
- Trigger workflow QA: `push` + `pull_request`, avec filtres backend/frontend/api-tests/workflow.
- Execution backend CI sur runner natif `ubuntu-latest` avec `./mvnw`.
- Execution frontend CI sur runner natif `ubuntu-latest` avec `npm ci`.
- Le wrapper Maven du depot porte la version Maven, `setup-java` ne gere que le JDK 21 et le cache Maven.
- Cache Maven gere par `actions/setup-java@v4` avec `cache: 'maven'` dans chaque job.
- Cache npm frontend gere par `actions/setup-node@v4` avec `cache: 'npm'` et `cache-dependency-path: frontend/package-lock.json`.

## Docker
- Dockerfile backend multi-stage: prevu (non versionne pour le moment).
- Dockerfile frontend multi-stage + nginx: prevu (non versionne pour le moment).
- Orchestration locale PostgreSQL via `docker-compose.yml`.

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

### Commandes minimales de verification CI frontend
- `cd frontend`
- `npm ci`
- `npm run lint`
- `npm run test`
- `npm run build`
- Attendu: `LINT_OK`, `TEST_OK`, `BUILD_OK`

### Commandes minimales de verification API smoke partage (Bruno)
- `docker compose up -d postgres`
- `cd backend/backend`
- `$env:DB_URL='jdbc:postgresql://localhost:5432/livechat'`
- `$env:DB_USER='livechat'`
- `$env:DB_PASSWORD='livechat'`
- `$env:APP_CORS_ALLOWED_ORIGINS='http://127.0.0.1:5173,http://localhost:5173'`
- `.\mvnw.cmd -DskipTests spring-boot:run`
- Dans un deuxieme terminal: `cd api-tests`
- `npx @usebruno/cli run ./auth --env local`
- Attendu: 4 requetes passees et 9 tests Bruno verts.

### Hygiene secrets API tests (CI/Sonar)
- Les fichiers `api-tests/environments/*.bru` doivent contenir uniquement des valeurs de test non sensibles (placeholders), jamais de secrets reels.
- Convention actuelle: `authPass` et `invalidPass` avec valeurs deterministes de test pour eviter les faux positifs de secret scanning dans les pipelines.
- En cas de besoin, surcharger ces variables uniquement via environnement CI, sans commiter de credentials.

### Commandes minimales de verification E2E frontend (Playwright)
- `docker compose up -d postgres`
- `cd backend/backend`
- `$env:DB_URL='jdbc:postgresql://localhost:5432/livechat'`
- `$env:DB_USER='livechat'`
- `$env:DB_PASSWORD='livechat'`
- `$env:APP_CORS_ALLOWED_ORIGINS='http://127.0.0.1:5173,http://localhost:5173'`
- `.\mvnw.cmd -DskipTests spring-boot:run`
- Dans un deuxieme terminal: `cd frontend`
- `npm ci`
- `npx playwright install chromium`
- `$env:VITE_API_BASE_URL='http://127.0.0.1:8080'`
- `$env:PLAYWRIGHT_BASE_URL='http://127.0.0.1:5173'`
- `npm run e2e`
- Attendu: scenarios register/login et erreur credentials passent.

### Branch protection backend
- `branch-ruleset.json` exige pour `main`:
	- `CI Backend / Lint (Checkstyle)`
	- `CI Backend / Unit Tests (JUnit 5)`
	- `CI Backend / Build (Maven)`

### Branch protection frontend
- Les checks frontend sont maintenant disponibles via le workflow CI Frontend.
- Leur activation comme checks requis de merge est en cours dans B.3.3.

### Branch protection QA
- Checks candidats pour required status checks:
	- `CI QA / API Smoke (Bruno)`
	- `CI QA / Frontend E2E (Playwright)`

## Trace Jira
- Ticket(s): B.3.3.



