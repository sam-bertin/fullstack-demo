# Frontend

## Stack
- **Installe actuel**: React 19, TypeScript strict, Vite.
- **Librairies ajoutees**: Axios, React Hook Form, Zod, hookform/resolvers.
- **Prevu prochainement**: Tailwind.

## Organisation
- **Partiellement implemente**: `features/auth` (components/hooks/schemas/types), `shared/lib` (api client).
- **Prevu**: extension vers `features/chat`, `shared/ui`, store global.

## Etat
- **Prevu prochainement**:
  - Serveur: TanStack Query.
  - Client: Zustand (ou Context si justifie).

## Flux UI
- Authentification minimale register/login (Implémente).
- Navigation et routes protegees (Prevu).
- Chat temps reel (Prevu).

## Validation formulaires
- **Implémente**: React Hook Form + Zod sur formulaires login/register.

## Client API
- **Partiellement implemente**:
  - Configuration Axios centralisee.
  - Appels API register/login.
  - Gestion d'erreur de base.
- **Prevu**:
  - Intercepteur JWT et gestion auth complete.

## Tests
- Vitest actif avec test smoke pour la CI.
- Strategie a etendre sur composants/hooks critiques.

## Trace Jira
- Ticket(s): B.2.1 initialise (socle React + Vite + TypeScript strict).
- Ticket(s): C.2.1 UI auth minimale.
- Documentation de tache: docs/03_frontend/B2_1_react_vite_typescript_strict.md
- Documentation de tache: docs/03_frontend/C2_1_auth_forms_minimal.md
- Evolution de plan: adoption React 19 validee et tracee dans docs/05_decisions/DECISIONS_LOG.md.
