# Architecture

## Vue d'ensemble
- **Implemented**: monorepo avec un backend Spring Boot (`backend/backend`) et un frontend React/Vite (`frontend`).
- **Planned**: flux metier complets auth + chat 1-to-1 avec persistance PostgreSQL.

## Couches backend
- **Implemented** (B.3.1): base applicative `com.livechat.backend` et squelette de packages:
  - `controller`, `service`, `serviceimpl`, `repository`, `dto`, `config`, `exception`.
- **Planned** (A.1.1, A.1.2, A.2.1, A.2.2): implementation des contrats API, du mapping d'erreurs et des validations metier.

## Architecture frontend
- **Implemented** (B.2.3): structure feature-based initiale:
  - `frontend/src/features/auth`, `frontend/src/features/chat`, `frontend/src/shared/lib`, `frontend/src/shared/ui`.
- **Planned** (B.2.2, C.2.*, D.2.*): etat global, client API JWT, formulaires, routes protegees, UX chat.

## CI/CD et gouvernance qualite
- **Implemented** (A.3.3, B.3.1, B.3.2, B.3.3):
  - Workflows `.github/workflows/ci-backend.yml` et `.github/workflows/ci-frontend.yml`.
  - Politique de declenchement **push-only** avec filtres de chemins.
  - Source de verite branch protection: `branch-ruleset.json`.
- **Planned**: extension des tests metier critiques (E.3.*).

## Trace Jira
- A.1.1, A.1.2, A.2.1, A.2.2, A.3.3, B.2.3, B.3.1, B.3.2, B.3.3.
