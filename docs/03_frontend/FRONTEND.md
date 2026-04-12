# Frontend

## Stack
- React 19, TypeScript strict, Vite, Tailwind.

## Statut global
- **Implemented**
  - Socle Vite React TypeScript strict (B.2.1).
  - CI frontend lint/test/build (B.3.2).
  - Structure feature-based minimale (B.2.3) avec shells `auth` et `chat`.
- **Planned**
  - Integration metier auth/chat.
  - Routing applicatif.
  - State server/client complet.

## Organisation
- **Implemented**
  - `src/features/auth`
  - `src/features/chat`
  - `src/shared/lib`
  - `src/shared/ui`
- **Planned**
  - decoupage par hooks/services/stores par feature.

## Etat
- **Planned**
  - Serveur: TanStack Query.
  - Client: Zustand (ou Context si justifie).

## Flux UI
- **Implemented**: shell d'application de bootstrap uniquement.
- **Planned**:
  - Authentification.
  - Navigation et routes protegees.
  - Chat temps reel.

## Validation formulaires
- **Planned**: React Hook Form + Zod/Yup.

## Client API
- **Planned**
  - Configuration Axios.
  - Intercepteurs JWT.
  - Gestion erreurs 401/403.

## Dependances coeur (B.2.2)
- **Installed now**
  - `react`, `react-dom`
  - stack de build/test/lint (`vite`, `typescript`, `eslint`, `vitest`, plugins associes)
- **Planned next**
  - `react-router-dom`
  - `axios`
  - `@tanstack/react-query`
  - `zustand`
  - `react-hook-form`
  - `zod`
  - `tailwindcss` (+ toolchain associee)

## Tests
- Vitest actif avec test smoke pour la CI.
- Strategie a etendre sur composants/hooks critiques.

## Trace Jira
- B.2.1, B.2.2, B.2.3, B.3.2, A.3.2.
