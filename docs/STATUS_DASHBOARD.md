# Status Dashboard (Jira -> Implementation)

| Jira key | Scope | Status | Evidence |
|---|---|---|---|
| A.1.1 | Contrat ApiResponse | Planned | `docs/02_backend/A1_1_api_response_standard.md` |
| A.1.2 | Mapping erreurs | Planned | `docs/02_backend/A1_2_error_mapping_strategy.md` |
| A.2.1 | Conventions DTO | Planned | `docs/02_backend/A2_1_dto_request_response_conventions.md` |
| A.2.2 | Politique validation | Planned | `docs/02_backend/A2_2_validation_policy.md` |
| A.3.1 | DoD backend | Implemented (doc) | `docs/02_backend/A3_1_backend_definition_of_done.md` |
| A.3.2 | DoD frontend | Implemented (doc) | `docs/03_frontend/A3_2_frontend_definition_of_done.md` |
| A.3.3 | DoD CI/CD | Implemented | `docs/04_devops/A3_3_cicd_definition_of_done.md` |
| B.1.1 | Initialisation Spring Boot | Implemented | `backend/backend/pom.xml`, `backend/backend/src/main/java/com/livechat/backend/BackendApplication.java` |
| B.1.3 | Architecture en couches (squelette) | Planned | `docs/02_backend/BACKEND.md` |
| B.2.1 | React + Vite + TS strict | Implemented | `frontend/package.json`, `frontend/tsconfig.app.json` |
| B.2.2 | Dependances coeur frontend | Planned clarified | `docs/03_frontend/FRONTEND.md` |
| B.2.3 | Structure feature-based frontend | Planned | `docs/03_frontend/FRONTEND.md` |
| B.3.1 | CI backend | Implemented | `.github/workflows/ci-backend.yml` |
| B.3.2 | CI frontend | Implemented | `.github/workflows/ci-frontend.yml` |
| B.3.3 | Merge quality gate | Implemented | `branch-ruleset.json` |

## Mise a jour obligatoire a la cloture de ticket
- Mettre a jour le statut de la cle Jira.
- Ajouter la preuve (fichier(s) modifies).
- Verifier coherence avec `PROJECT_PLAN.md`.
