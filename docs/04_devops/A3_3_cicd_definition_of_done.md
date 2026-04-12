# Fiche de Tache Jira - A.3.3 Definir DoD CI/CD

## 1) Identification
- **Cle Jira :** A.3.3
- **Epic :** EPIC A - Gouvernance Architecture et Qualite
- **Date :** 2026-04-12

## 2) Decision appliquee
- Politique CI retenue et appliquee: **push-only** avec filtres de chemins.
- Les workflows reels (`ci-backend.yml`, `ci-frontend.yml`) et la documentation sont alignes sur cette politique.
- La source de verite de la protection de branche est versionnee dans le repository via `branch-ruleset.json`.

## 3) Implementation en place
### Workflows
- `.github/workflows/ci-backend.yml`
  - `Lint (Checkstyle)` -> `Unit Tests (JUnit 5)` -> `Build (Maven)`
- `.github/workflows/ci-frontend.yml`
  - `Lint (ESLint)` -> `Unit Tests (Vitest)` -> `Build (Vite)`

### Trigger
- Backend: `push` uniquement (filtres backend + workflow).
- Frontend: `push` uniquement (filtres frontend + workflow).

### Branch protection versionnee
- Fichier: `/home/runner/work/fullstack-demo/fullstack-demo/branch-ruleset.json`
- Checks requis:
  - `CI Backend / Lint (Checkstyle)`
  - `CI Backend / Unit Tests (JUnit 5)`
  - `CI Backend / Build (Maven)`
  - `CI Frontend / Lint (ESLint)`
  - `CI Frontend / Unit Tests (Vitest)`
  - `CI Frontend / Build (Vite)`

## 4) DoD CI/CD (etat actuel)
- [x] Workflows backend/frontend actifs.
- [x] Lint/tests/build executes automatiquement.
- [x] Nommage des checks aligne entre workflows et protection de branche.
- [x] Documentation de reproduction locale disponible.
- [x] Source de verite branch protection committee.

## 5) Verification locale
### Frontend (execute dans cet environnement)
- `npm ci`
- `npm run lint`
- `npm run test`
- `npm run build`

### Backend (limitation environnement)
- `./mvnw checkstyle:check`
- `./mvnw test`
- `./mvnw -DskipTests clean package`
- Observation: echec local sur JDK indisponible (`release version 21 not supported`) dans le sandbox actuel.

## 6) Impacts
- Controle qualite executable et versionne dans le depot.
- Moins d'ecarts entre politique documentee et comportement reel.

## 7) References
- `/home/runner/work/fullstack-demo/fullstack-demo/.github/workflows/ci-backend.yml`
- `/home/runner/work/fullstack-demo/fullstack-demo/.github/workflows/ci-frontend.yml`
- `/home/runner/work/fullstack-demo/fullstack-demo/branch-ruleset.json`
- `/home/runner/work/fullstack-demo/fullstack-demo/docs/04_devops/DEVOPS.md`

## 8) Texte Jira pret a coller
`A.3.3 aligne: workflows backend/frontend et documentation sont maintenant coherents sur une politique push-only. La protection de branche est versionnee dans branch-ruleset.json avec 6 checks requis aligns aux jobs CI.`
