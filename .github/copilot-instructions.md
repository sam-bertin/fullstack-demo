# CONTEXTE DU PROJET
Application web distribuée. Le backend expose une API RESTful sécurisée. Le frontend est une Single Page Application (SPA) réactive.
Priorités : Sécurité (Zero Trust local), maintenabilité (Clean Code, SOLID), scalabilité.

**PLAN DE PROJET :** Se référer au fichier `PROJECT_PLAN.md` à la racine pour suivre la feuille de route (EPIC -> Fonctionnalité -> Tâche). Ce fichier reflète l'état initial du Jira et ne doit pas être modifie pour reconsigner des decisions de conception. Les decisions, compromis, impacts et textes de synthese doivent etre traces dans `docs/` et dans les commentaires Jira proposes.
---

## 1. BACKEND (Java 21, Spring Boot 3.x, Maven, PostgreSQL)

### Architecture & Design Patterns
- Architecture en couches stricte : `Controller` -> `Service` (interface) -> `ServiceImpl` -> `Repository`.
- Injection de dépendances : Utiliser l'injection par constructeur (avec `@RequiredArgsConstructor` de Lombok), jamais par champ (`@Autowired`).
- Gestion des erreurs : Utiliser un `@RestControllerAdvice` global. Les contrôleurs ne doivent retourner que des objets DTO enveloppés dans un format standardisé (ex: `ApiResponse<T>`). Ne jamais exposer les entités JPA directement.

### Naming Conventions
- Packages : Minuscules, format `com.company.project.module`.
- Classes/Interfaces : `PascalCase` (ex: `UserProfileService`).
- Méthodes/Variables : `camelCase` (ex: `getUserById`).
- Constantes : `UPPER_SNAKE_CASE` (ex: `MAX_RETRY_COUNT`).
- DTOs : Suffixés par `Request` ou `Response` (ex: `UserCreationRequest`).

### Sécurité & Standards Techniques
- Authentification : Spring Security avec JWT (JSON Web Tokens) sans état.
- Mots de passe : Hachage avec BCrypt obligatoire.
- Validation : Utiliser `spring-boot-starter-validation` (annotations `@Valid`, `@NotNull`, `@Size` sur les DTOs).
- Tests : JUnit 5 et Mockito. Les tests unitaires doivent couvrir 80%+ de la couche Service.

---

## 2. FRONTEND (React 19, TypeScript, Vite, Tailwind CSS)

### Architecture & State Management
- Structure par "Features" : Regrouper les fichiers par domaine (`/features/auth`, `/features/users`) contenant leurs propres composants, hooks et types.
- Composants : Uniquement des composants fonctionnels. Séparer la logique (Custom Hooks) de l'UI (Dumb Components).
- Gestion d'état : 
  - État serveur : `TanStack Query` (React Query) pour le data fetching et la mise en cache.
  - État client global : `Zustand` ou Context API (uniquement si nécessaire).
- Requêtes HTTP : Utiliser `Axios` avec des intercepteurs pour injecter le token JWT automatiquement.

### Naming Conventions
- Fichiers de composants : `PascalCase.tsx` (ex: `UserProfile.tsx`).
- Fichiers de logique/hooks : `camelCase.ts` (ex: `useAuth.ts`, `apiClient.ts`).
- Types/Interfaces : `PascalCase`, de préférence préfixés par un `I` si interface (ex: `IUserData`) ou simplement nommés clairement (ex: `User`).

### Standards Techniques
- Typage : Strict. Le type `any` est formellement interdit. Définir des interfaces claires pour les props et les retours d'API.
- Validation : Utiliser `Zod` ou `Yup` combiné avec `React Hook Form` pour la validation des formulaires côté client.

---

## 3. CI/CD & DEVOPS (GitHub Actions, Docker)

### Processus et Workflows
- Utiliser GitHub Actions avec des workflows séparés : `ci-backend.yml`, `ci-frontend.yml`.
- Étapes obligatoires de la CI :
  1. Linting (Checkstyle pour Java, ESLint pour TS).
  2. Tests unitaires (Maven Test, Vitest/Jest).
  3. Build des artefacts (Jar, build Vite).
- Conteneurisation : Créer un `Dockerfile` multi-stage (builder -> runner) pour le backend et le frontend (avec Nginx) afin de minimiser le poids de l'image finale.

### Protocole Git & Naming Conventions
- SÉCURITÉ : La branche `main` est protégée. Le push direct est strictement interdit.
- Workflow obligatoire :
  1. Créer une branche depuis `main` (`feature/xxx`, `bugfix/xxx`, `chore/xxx`).
  2. Développer et commiter en suivant les Conventional Commits (`feat:`, `fix:`, `chore:`, `docs:`).
  3. Pousser la branche et créer une Pull Request vers `main`.
- IA : Si l'utilisateur te demande de sauvegarder ou de pousser du code, génère TOUJOURS les commandes pour créer une nouvelle branche et faire un push de cette branche, jamais vers `main`.
- Commits : Suivre les conventions de gitmoji.dev pour les messages de commit (ex: `✨ add user authentication`).

---

## 4. DOCUMENTATION & ONBOARDING (OBLIGATOIRE)

### Objectif
- La documentation fait partie du livrable, au meme titre que le code.
- Une fonctionnalite n'est pas consideree comme terminee tant que sa documentation n'est pas a jour.
- La documentation doit permettre a une personne externe de comprendre le "quoi", le "pourquoi", le "comment", et de reproduire l'environnement sans connaissance implicite.

### References projet
- Jira (source de verite du backlog et du statut) : https://reptar.atlassian.net/jira/software/c/projects/FUL/boards/3
- GitHub (source de verite du code) : https://github.com/sam-bertin/fullstack-demo

### Exigences de documentation (niveau hyper exhaustif)
- Documenter chaque tache Jira implementee, y compris le contexte, les choix, les alternatives et les impacts.
- Expliquer explicitement les decisions techniques et les compromis (performance, securite, complexite, cout de maintenance).
- Fournir les prerequis, commandes exactes, variables d'environnement requises, et resultats attendus.
- Decrire les schemas de donnees, contrats API, flux d'evenements et comportements d'erreur.
- Lier chaque changement a la cle Jira concernee (ex: `FUL-23`) dans la documentation technique.
- Maintenir une trace chronologique des evolutions (decision log / changelog technique).

### Structure documentaire attendue dans la codebase
- `docs/00_onboarding/` : installation locale, prerequis, premier demarrage, depannage de base.
- `docs/01_architecture/` : vues d'architecture, couches applicatives, conventions, diagrammes.
- `docs/02_backend/` : modules backend, endpoints, securite, validation, persistence.
- `docs/03_frontend/` : structure feature-based, flux UI, etat global, appels API.
- `docs/04_devops/` : CI/CD, Docker, workflows Git, procedure de release locale.
- `docs/05_decisions/` : ADR (Architecture Decision Records) et compromis.
- `docs/06_runbooks/` : procedures operationnelles (incident, rollback, verification).

### Rituels obligatoires lors de chaque implementation
- Avant implementation : verifier `PROJECT_PLAN.md` et identifier la cle Jira associee.
- Pendant implementation : noter decisions et hypotheses.
- Apres implementation : mettre a jour la documentation dans `docs/` avec exemples executables.
- Avant cloture de la tache : valider que code, tests, CI et documentation sont coherents.

### Workflow de collaboration obligatoire avec l'agent
- Sequence imposee : discussion -> validation des decisions -> documentation detaillee -> implementation.
- L'agent doit rediger la documentation, pas l'utilisateur.
- Tant que la decision n'est pas validee par l'utilisateur, l'agent ne lance pas l'implementation.
- Pour chaque tache Jira validee, l'agent doit creer ou mettre a jour une fiche basee sur `docs/TASK_DOC_TEMPLATE.md` dans le dossier `docs/` approprie (ex: `docs/02_backend/FUL-23.md`).
- La fiche doit etre creee/mise a jour avant toute implementation, puis finalisee en fin de tache avec resultats, tests et impacts.
- Chaque fiche doit inclure explicitement : contexte, choix retenus, alternatives, compromis, etapes realisees, verification, impacts, et references Jira/GitHub.
- L'agent doit aussi proposer, a la fin de chaque decision ou implementation importante, un texte court pret a coller dans Jira pour annoter le ticket (resume de la decision, impact, lien vers la documentation).
- Si une nouvelle fonctionnalite est decidee, l'agent doit proposer une evolution de `PROJECT_PLAN.md` seulement pour la reflecter dans Jira si le backlog change; sinon la trace de la decision reste dans `docs/05_decisions/` et dans la fiche de tache.

### Regle de definition de termine (DoD documentaire)
- Une tache est "Done" uniquement si :
  1. le code est implemente et teste,
  2. la documentation associee est mise a jour,
  3. la cle Jira est referencee dans les sections impactees,
  4. un onboarding partiel est possible sans explication orale.

---

## DIRECTIVES GÉNÉRALES POUR L'IA
- Fournis uniquement du code prêt pour la production.
- Ajoute des commentaires JSDoc/JavaDoc sur les méthodes complexes.
- N'invente pas de dépendances ; utilise les standards de l'écosystème spécifié.
- Si une modification nécessite de mettre à jour les variables d'environnement (`.env`), signale-le explicitement.
- Toute modification technique doit inclure la mise a jour de la documentation correspondante dans `docs/`.