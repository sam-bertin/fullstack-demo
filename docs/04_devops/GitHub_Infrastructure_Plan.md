# Etat Infrastructure GitHub (B.3.1 / B.3.2 / B.3.3)

## Identification
- **Cles Jira :** B.3.1, B.3.2, B.3.3
- **Date de consolidation :** 2026-04-12

## Politique CI appliquee
- Politique retenue: **push-only** avec filtres de chemins.
- Workflows actifs:
  - `/home/runner/work/fullstack-demo/fullstack-demo/.github/workflows/ci-backend.yml`
  - `/home/runner/work/fullstack-demo/fullstack-demo/.github/workflows/ci-frontend.yml`

## Source de verite branch protection
- Fichier versionne: `/home/runner/work/fullstack-demo/fullstack-demo/branch-ruleset.json`
- Required status checks:
  - `CI Backend / Lint (Checkstyle)`
  - `CI Backend / Unit Tests (JUnit 5)`
  - `CI Backend / Build (Maven)`
  - `CI Frontend / Lint (ESLint)`
  - `CI Frontend / Unit Tests (Vitest)`
  - `CI Frontend / Build (Vite)`

## Cohérence workflows <-> ruleset
- Le nom du workflow est le prefixe du check context (`CI Backend`, `CI Frontend`).
- Le nom de job correspond au suffixe (`Lint`, `Unit Tests`, `Build`).
- Les checks requis dans `branch-ruleset.json` sont alignes avec les jobs actuels.

## Verification locale recommandee
### Backend
- `cd /home/runner/work/fullstack-demo/fullstack-demo/backend/backend`
- `./mvnw checkstyle:check`
- `./mvnw test`
- `./mvnw -DskipTests clean package`

### Frontend
- `cd /home/runner/work/fullstack-demo/fullstack-demo/frontend`
- `npm ci`
- `npm run lint`
- `npm run test`
- `npm run build`

## Texte Jira pret a coller
`B.3.1/B.3.2/B.3.3 alignes: workflows backend/frontend actifs en push-only, checks lint/test/build confirms, et branch-ruleset.json versionne comme source de verite pour les checks requis de merge.`
