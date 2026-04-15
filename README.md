# Fullstack Demo Project

Bienvenue sur le depot de l'application Fullstack Demo (Spring Boot + React).

## État Actuel du Projet (Status Rapide)

Le projet est actuellement en phase de **Bootstrap technique**.

Ce qui est **reellement implemente** dans le code aujourd'hui :
- **Backend** : Un projet Spring Boot 3 de base généré via start.spring.io avec Java 21, Maven, et les dépendances principales définies (JPA, Web, Security, Validation). Il compile et les tests JUnit simples passent.
- **Frontend** : Un squelette Vite avec React 19 et TypeScript. Il compile et le lint passe.
- **CI/CD** : Des workflows GitHub Actions sont actifs pour builder et tester le backend (Checkstyle, Maven Test) et le frontend (ESLint, Vitest, npm build) sur chaque push. Une protection de branche de base est active sur `main` (revue obligatoire, pas de direct push).
- **Documentation** : De nombreux documents d'architecture, de conventions, et de Definition of Done (DoD) ont été écrits dans le dossier `docs/` pour préparer la suite.

Ce qui **n'est pas encore implemente** (mais documenté comme cible) :
- La structure en couches (Controller, Service, Repository) du backend.
- La structure en features (features/auth, features/chat, etc.) du frontend.
- Les autres librairies (Tailwind, React Query, Zustand, Axios, React Hook Form, Zod).
- L'authentification (JWT), la base de données PostgreSQL, ou toute logique métier.

👉 **Veuillez consulter `docs/STATUS.md` pour une matrice détaillée de l'avancement ticket par ticket.**

## Quickstart (Pour tester l'existant)

Ce guide permet de lancer et verifier ce qui est reellement present aujourd'hui.

### Pre-requis
- Java 21
- Maven 3.9+
- Node.js 22

### Backend
```bash
cd backend/backend
./mvnw checkstyle:check
./mvnw test
./mvnw -DskipTests clean package
```

### Frontend
```bash
cd frontend
npm ci
npm run lint
npm run test
npm run build
```

## Documentation

La documentation de reference et l'onboarding detaillé se trouvent dans le dossier `docs/` :
- `docs/00_onboarding/ONBOARDING.md` : Guide complet d'installation et dépannage.
- `docs/01_architecture/` : Plans d'architecture prévus.
- `docs/05_decisions/DECISIONS_LOG.md` : Historique des décisions techniques (ADR).
- `PROJECT_PLAN.md` : La roadmap complète.
