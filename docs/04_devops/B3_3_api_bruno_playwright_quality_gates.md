# B.3.3 - Gate qualite merge API smoke + E2E UI

## 1) Identification

### Metadonnees
- Cle Jira : B.3.3
- Epic : EPIC B - Fondations Techniques
- Fonctionnalite : B.3 - Gate de qualite merge
- Tache : Ajouter des gates PR pour tests API partages (Bruno) et E2E frontend (Playwright)
- Date : 2026-04-15
- Auteur : GitHub Copilot

### Liens de travail
- Branche Git : a renseigner
- Pull Request : a renseigner

## 2) Contexte et objectif

### Probleme
Les validations manuelles auth sont possibles, mais il manque un socle partage et automatisable pour les tests API et les interactions utilisateur de bout en bout.

### Justification
B.3.3 vise a rendre les checks critiques executables en CI avant merge. L'equipe a valide Bruno pour les requetes API partagees et Playwright pour les parcours UI.

### Valeur attendue
- Requetes API auth rejouables par tous les developpeurs.
- Scenarios E2E utilisateur automatises.
- Jobs CI PR dedies et exploitables (logs/artifacts).

## 3) Perimetre

### Inclus
- Bibliotheque `api-tests/` Bruno versionnee dans le repo.
- Scenarios API auth succes + erreurs principales.
- Setup Playwright frontend + scenarios E2E auth.
- Integration CI PR pour API smoke et E2E.
- Mise a jour documentation DevOps/Status/Decisions.

### Exclu
- Flows JWT complets (tickets C.1.4/C.2.3).
- Scenarios E2E multi-role avances.
- Tests de performance charge.

## 4) Conception technique

### Solution retenue
- Bruno comme format officiel de tests API partages (`.bru`) avec environnement local/ci.
- Playwright pour E2E frontend (register/login + erreur utilisateur).
- Workflows CI separant API smoke et E2E pour lisibilite des echecs.

### Alternatives considerees
- Fichiers `.http` uniquement (VS Code REST Client).
- Cypress pour E2E.
- Tests API uniquement sans E2E UI.

### Justification du choix
- Bruno: format git-friendly + execution CLI simple.
- Playwright: robuste en CI, traces de debug, support moderne TS.
- Separation des jobs: diagnostics plus rapides en PR.

### Compromis
- Performance : pipeline PR plus long.
- Securite : meilleure prevention des regressions avant merge.
- Complexite : outillage CI plus riche.
- Maintenance : dette reduite grace a tests versionnes et documentes.

## 5) Implementation detaillee

### Fichiers modifies
- `.github/workflows/ci-backend.yml`
- `.github/workflows/ci-frontend.yml`
- `.github/workflows/ci-qa.yml`
- `api-tests/bruno.json`
- `api-tests/environments/local.bru`
- `api-tests/environments/ci.bru`
- `api-tests/auth/01_register_success.bru`
- `api-tests/auth/02_login_success.bru`
- `api-tests/auth/03_login_invalid_password.bru`
- `api-tests/auth/04_register_validation_error.bru`
- `api-tests/README.md`
- `frontend/package.json`
- `frontend/package-lock.json`
- `frontend/.gitignore`
- `frontend/playwright.config.ts`
- `frontend/src/e2e/auth.e2e.ts`
- `docs/04_devops/B3_3_api_bruno_playwright_quality_gates.md`
- `docs/04_devops/DEVOPS.md`
- `docs/03_frontend/FRONTEND.md`
- `docs/00_onboarding/ONBOARDING.md`
- `docs/05_decisions/DECISIONS_LOG.md`
- `docs/STATUS.md`

### Changement par fichier
- `ci-backend.yml` / `ci-frontend.yml` : ajout du trigger `pull_request` pour aligner les checks sur PR.
- `ci-qa.yml` : nouveau workflow QA avec 2 jobs (Bruno API smoke puis Playwright E2E), demarrage backend + PostgreSQL, upload artifacts.
- `api-tests/*` : collection Bruno partagee (success + erreurs auth), environnements local/ci et guide d'execution.
- `frontend/package*.json` : ajout de `@playwright/test` et scripts `e2e`, `e2e:ui`.
- `frontend/playwright.config.ts` + `frontend/src/e2e/auth.e2e.ts` : setup E2E et scenarios register/login + invalid credentials.
- `frontend/.gitignore` : exclusion des artifacts Playwright.
- Documentation : mise a jour DevOps/Onboarding/Status/Decisions et finalisation de la fiche B.3.3.

### Contrats impactes
- API : endpoints auth testes via collection Bruno.
- Evenements : aucun.
- Base de donnees : aucun schema supplementaire.

## 6) Configuration et environnement

### Variables d'environnement
- Ajoutees/modifiees : `PLAYWRIGHT_BASE_URL`, `VITE_API_BASE_URL` (execution E2E), `APP_CORS_ALLOWED_ORIGINS` (execution backend pour E2E), `DB_URL`, `DB_USER`, `DB_PASSWORD` (backend local/CI).
- Valeurs d'exemple non sensibles :
	- `PLAYWRIGHT_BASE_URL=http://127.0.0.1:5173`
	- `VITE_API_BASE_URL=http://127.0.0.1:8080`
	- `APP_CORS_ALLOWED_ORIGINS=http://127.0.0.1:5173,http://localhost:5173`
	- `DB_URL=jdbc:postgresql://localhost:5432/livechat`
	- `DB_USER=livechat`
	- `DB_PASSWORD=livechat`

### Prerequis
- Docker actif (PostgreSQL local/Testcontainers).
- Node.js 22 et Java 21.

## 7) Base de donnees

### Impacts schema
- Entites/tables : aucun.
- Migrations : aucune.
- Compatibilite : conservation schema existant.

## 8) API et evenements

### API
- Endpoints ajoutes/modifies : aucun endpoint backend nouveau.
- Payload request/response : scenarios de test sur endpoints auth existants.
- Codes d'erreur et cas limites : validation_error, invalid_credentials, conflict.

### Evenements
- Flux d'evenements (si applicable) : aucun.

## 9) Securite

### Analyse
- Risques identifies : absence de verification systematique des erreurs API et parcours UI critiques.
- Mesures appliquees : tests API partages + E2E PR.
- Verifications effectuees : scenarios invalid credentials + validation payload invalide integres a la collection/API et verifies localement.

## 10) Tests et validation

### Tests
- Unitaires : inchanges.
- Integration/E2E : Bruno API smoke + Playwright UI E2E.

### Resultats
- `backend/backend`: `./mvnw.cmd test` -> **PASS** (8 tests, 0 erreur, 0 echec).
- `frontend`: `npm run lint && npm run test && npm run build` -> **PASS**.
- Bruno local: `npx @usebruno/cli run ./api-tests/auth --env local` -> **PASS** (4 requetes, 9 tests).
- Playwright local: `npm run e2e` -> **PASS** (2 scenarios).

### Verification manuelle
- Backend demarre en profil `dev` sur PostgreSQL local.
- Scenarios register/login executes via UI et via collection Bruno.
- Erreurs API attendues observees: `INVALID_CREDENTIALS`, `VALIDATION_ERROR`.

## 11) CI/CD et exploitation

### Impacts pipeline
- Workflows impactes : `ci-backend.yml`, `ci-frontend.yml`, `ci-qa.yml`.
- Build/deploiement impactes : checks PR supplementaires (API smoke + E2E).
- Etat merge gate : required checks backend actifs ; required checks frontend/QA encore a activer dans la protection de branche.

### Exploitation
- Logs/monitoring/runbooks impactes : artifacts Bruno + Playwright en cas d'echec.

## 12) Documentation mise a jour

### Documents modifies
- docs/04_devops/B3_3_api_bruno_playwright_quality_gates.md
- docs/04_devops/DEVOPS.md
- docs/03_frontend/FRONTEND.md
- docs/00_onboarding/ONBOARDING.md
- docs/05_decisions/DECISIONS_LOG.md
- docs/STATUS.md

### Resume documentaire
Fiche creee avant implementation puis finalisee avec resultats de validation locale et details CI.

## 13) Definition of Done

- Code implemente et teste : Oui
- Documentation mise a jour : Oui
- Cle Jira referencee dans docs : Oui
- Onboarding possible sans oral : Oui
- Statut global B.3.3 : Partiel (activation required status checks frontend + QA en attente)

## 14) Trace de decision

- Reference Decision Log : docs/05_decisions/DECISIONS_LOG.md
- Action(s) de suivi : activer les checks frontend/QA comme required status checks, puis integrer les scenarios JWT quand C.1.4/C.2.3 sera livre.

## 15) Changelog tache

- v1 : creation de la fiche avant implementation.
- v2 : implementation Bruno + Playwright + workflow QA PR.
- v3 : validation locale complete et finalisation documentaire.

## 16) Texte Jira pret a coller

- Resume court de la decision : lancement B.3.3 avec Bruno (API smoke partage) et Playwright (E2E UI) executes en PR.
- Impact principal : verification automatique des interactions API/UI critiques avant merge.
- Documentation associee : docs/04_devops/B3_3_api_bruno_playwright_quality_gates.md
- Point d'attention pour le prochain ticket : activer required checks frontend/QA puis etendre la couverture aux endpoints JWT proteges.