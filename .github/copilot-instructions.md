# CONTEXTE DU PROJET
Application web distribuée. Le backend expose une API RESTful sécurisée. Le frontend est une Single Page Application (SPA) réactive.
Priorités : Sécurité (Zero Trust local), maintenabilité (Clean Code, SOLID), scalabilité.

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

## 2. FRONTEND (React 18, TypeScript, Vite, Tailwind CSS)

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

### Naming Conventions Git
- Branches : `feature/nom-de-la-feature`, `bugfix/nom-du-bug`, `main`.
- Commits : Suivre les Conventional Commits (`feat:`, `fix:`, `chore:`, `docs:`).

---

## DIRECTIVES GÉNÉRALES POUR L'IA
- Fournis uniquement du code prêt pour la production.
- Ajoute des commentaires JSDoc/JavaDoc sur les méthodes complexes.
- N'invente pas de dépendances ; utilise les standards de l'écosystème spécifié.
- Si une modification nécessite de mettre à jour les variables d'environnement (`.env`), signale-le explicitement.