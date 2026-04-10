# Plan Infrastructure GitHub pour B.3.1 et B.3.2

**Clé Jira** : B.3.1 (Workflow CI backend) et B.3.2 (Workflow CI frontend)  
**Date** : 2026-04-10 (Planning)  
**Statut** : Plan (implémentation reportée à B.3.1/B.3.2)

---

## Vue d'ensemble

Ce document définit la structure et le contenu des workflows GitHub Actions qui seront **créés lors de B.3.1 et B.3.2**.

Les workflows doivent implémenter automatiquement les gates définies dans la DoD (A.3.1, A.3.2, A.3.3) :
1. **Lint** : Checkstyle (backend) / ESLint (frontend)
2. **Tests** : JUnit 5 (backend) / Vitest (frontend)
3. **Build** : Maven (backend) / Vite (frontend)

---

## Workflow Backend (`B.3.1`)

### Fichier
`.github/workflows/ci-backend.yml`

### Déclenchement
- Push vers `main`, `feature/**`, `bugfix/**`, `chore/**`
- PR vers `main`
- Modifications dans `backend/` ou `.github/workflows/ci-backend.yml`

### Étapes

| # | Job | Command | Status Check |
|---|-----|---------|--------------|
| 1 | Lint | `mvn checkstyle:check` | `CI Backend / Lint (Checkstyle)` |
| 2 | Test | `mvn clean test` | `CI Backend / Unit Tests (JUnit 5)` |
| 3 | Build | `mvn clean package -DskipTests` | `CI Backend / Build (Maven)` |

**Build dépend** de Lint + Test (séquentiel).

### Environnement
- Runner : `ubuntu-latest`
- Java : 21 (Temurin)
- Cache : Maven activé

### Artifacts
- Upload JAR (retention 5 jours)

---

## Workflow Frontend (`B.3.2`)

### Fichier
`.github/workflows/ci-frontend.yml`

### Déclenchement
- Push vers `main`, `feature/**`, `bugfix/**`, `chore/**`
- PR vers `main`
- Modifications dans `frontend/` ou `.github/workflows/ci-frontend.yml`

### Étapes

| # | Job | Command | Status Check |
|---|-----|---------|--------------|
| 1 | Lint | `npm run lint` | `CI Frontend / Lint (ESLint)` |
| 2 | Test | `npm run test` | `CI Frontend / Unit Tests (Vitest)` |
| 3 | Build | `npm run build` | `CI Frontend / Build (Vite)` |

**Build dépend** de Lint + Test (séquentiel).

### Environnement
- Runner : `ubuntu-latest`
- Node.js : 20
- Cache : npm activé

### Artifacts
- Upload dist/ (retention 5 jours)

---

## Branch Protection Integration (`B.3.3`)

Une fois les workflows de B.3.1 et B.3.2 actifs, les **required status checks** suivants seront **activés** dans `branch-ruleset.json` :

```json
{
  "type": "required_status_checks",
  "parameters": {
    "strict_required_status_checks_policy": true,
    "required_status_checks": [
      { "context": "CI Backend / Lint (Checkstyle)" },
      { "context": "CI Backend / Unit Tests (JUnit 5)" },
      { "context": "CI Backend / Build (Maven)" },
      { "context": "CI Frontend / Lint (ESLint)" },
      { "context": "CI Frontend / Unit Tests (Vitest)" },
      { "context": "CI Frontend / Build (Vite)" }
    ]
  }
}
```

---

## Prérequis pour l'implémentation

### Backend (B.1.1)
- `pom.xml` avec dépendances CheckStyle, JUnit 5
- `src/test/java` avec au moins un test d'exemple
- Configuration CheckStyle (convention de code)

### Frontend (B.2.1)
- `package.json` avec scripts `lint`, `test`, `build`
- Configuration ESLint
- Configuration Vitest

---

## Cas d'usage d'exécution

### PR Backend uniquement
- ✅ `ci-backend.yml` s'exécute
- ⏭️ `ci-frontend.yml` skippé (pas de modifs frontend)

### PR Frontend uniquement
- ⏭️ `ci-backend.yml` skippé (pas de modifs backend)
- ✅ `ci-frontend.yml` s'exécute

### PR Backend + Frontend
- ✅ Tous deux s'exécutent en parallèle

### Merge bloqueú si
- ❌ Lint échoue (lint check rouge)
- ❌ Tests échouent (test check rouge)
- ❌ Build échoue (build check rouge)

---

## Débogage futur

### Tester localement (pré-PR)

**Backend** :
```bash
cd backend
mvn clean checkstyle:check
mvn clean test
mvn clean package -DskipTests
```

**Frontend** :
```bash
cd frontend
npm ci
npm run lint
npm run test
npm run build
```

### Relancer workflow GitHub

Via `Actions` → Sélectionner run → "Re-run failed jobs"

---

## Limitations et notes

1. **Workflows ne s'exécutent que si les projets existent** (backend/, frontend/ avec pom.xml, package.json)
2. **Status check names doivent matcher exactement** entre workflows et branch-ruleset.json
3. **Artifacts retention fixée à 5 jours** (ajustable selon besoins)
4. **Approuver les PRs** : Au minimum 1 review requis (par branche-ruleset.json actuel)

---

## Prochaines étapes

- ⏳ **B.1.1** : Créer backend/ avec pom.xml
- ⏳ **B.2.1** : Créer frontend/ avec package.json
- ⏳ **B.3.1** : Créer `.github/workflows/ci-backend.yml`
- ⏳ **B.3.2** : Créer `.github/workflows/ci-frontend.yml`
- ⏳ **B.3.3** : Activer `required_status_checks` dans `branch-ruleset.json`
