# B.3.2 - Creer workflow CI frontend

## 1) Identification

### Metadonnees
- Cle Jira : B.3.2
- Epic : EPIC B - Fondations Techniques
- Fonctionnalite : B.3 - CI qualite de base
- Tache : Creer workflow CI frontend
- Date : 2026-04-10
- Auteur : GitHub Copilot

### Liens de travail
- Branche Git : a renseigner
- Pull Request : a renseigner

## 2) Contexte et objectif

### Probleme
La CI backend est en place, mais il manque encore une CI frontend pour detecter les regressions lint, tests et build avant merge.

### Justification
La tache B.3.2 du plan exige un workflow frontend equivalent au backend sur les gates de qualite.

### Valeur attendue
Avoir un pipeline frontend automatique et reproductible localement.

## 3) Perimetre

### Inclus
- Workflow GitHub Actions frontend en jobs separes (lint, tests, build).
- Script de tests frontend executable en CI.
- Documentation DevOps et onboarding associee.

### Exclu
- Activation des required status checks dans la protection de branche (B.3.3).
- E2E frontend.

## 4) Conception technique

### Solution retenue
Aligner la structure du workflow frontend sur le workflow backend : jobs sequentiels, installation dependencies, execution lint/tests/build, et publication de l'artefact de build.

### Alternatives considerees
- Un seul job monolithique pour lint + tests + build.
- Workflow frontend sans etape tests.

### Justification du choix
Le decoupage en jobs clarifie les causes d'echec et reproduit la discipline appliquee au backend.

### Compromis
- Performance : pipeline plus long qu'un job unique.
- Securite : reduction des regressions qui atteignent main.
- Complexite : legere augmentation de maintenance CI.
- Maintenance : forte lisibilite des etapes et des echecs.

## 5) Implementation detaillee

### Fichiers modifies
- .github/workflows/ci-frontend.yml
- frontend/package.json
- frontend/package-lock.json
- frontend/src/smoke.test.ts
- docs/04_devops/B3_2_ci_frontend_workflow.md
- docs/04_devops/DEVOPS.md
- docs/00_onboarding/ONBOARDING.md
- docs/05_decisions/DECISIONS_LOG.md
- docs/03_frontend/FRONTEND.md

### Changement par fichier
- .github/workflows/ci-frontend.yml : ajout d'un workflow CI Frontend sur push avec 3 jobs sequentiels (Lint ESLint -> Unit Tests Vitest -> Build Vite) et publication de l'artefact dist.
- frontend/package.json : ajout du script `test` avec `vitest run`.
- frontend/package-lock.json : verrouillage des dependances apres ajout de Vitest.
- frontend/src/smoke.test.ts : ajout d'un test unitaire smoke minimal pour garantir une execution test stable en CI.
- docs/04_devops/DEVOPS.md : mise a jour de l'etat CI frontend et des commandes minimales de verification locale.
- docs/00_onboarding/ONBOARDING.md : ajout du journal d'execution B.3.2 (commandes et resultats).
- docs/05_decisions/DECISIONS_LOG.md : ajout de la decision technique B.3.2.
- docs/03_frontend/FRONTEND.md : mise a jour de l'etat des tests frontend (Vitest actif).

### Contrats impactes
- API : aucun
- Evenements : aucun
- Base de donnees : aucun

## 6) Configuration et environnement

### Variables d'environnement
- Ajoutees/modifiees : aucune
- Valeurs d'exemple non sensibles : sans objet

### Prerequis
- Node.js et npm installes.

## 7) Base de donnees

### Impacts schema
- Entites/tables : aucun
- Migrations : aucune
- Compatibilite : sans objet

## 8) API et evenements

### API
- Endpoints ajoutes/modifies : aucun
- Payload request/response : sans objet
- Codes d'erreur et cas limites : sans objet

### Evenements
- Flux d'evenements (si applicable) : aucun

## 9) Securite

### Analyse
- Risques identifies : merge de regressions frontend sans verification automatique.
- Mesures appliquees : workflow CI frontend obligatoire en PR/merge apres B.3.3.
- Verifications effectuees : execution locale de `npm run lint`, `npm run test` et `npm run build` avec resultats verts.

## 10) Tests et validation

### Tests
- Unitaires : Vitest actif avec un test smoke (`frontend/src/smoke.test.ts`).
- Integration/E2E : non inclus dans B.3.2.

### Resultats
- `npm run lint` : OK.
- `npm run test` : OK (1 fichier, 1 test passe).
- `npm run build` : OK (generation de `dist/`).

### Verification manuelle
- Validation locale de la sequence CI frontend complete, dans le meme esprit que la CI backend (lint -> test -> build).

## 11) CI/CD et exploitation

### Impacts pipeline
- Workflows impactes : ci-frontend.yml (nouveau)
- Build/deploiement impactes : ajout de l'artefact frontend

### Exploitation
- Logs/monitoring/runbooks impactes : aucun

## 12) Documentation mise a jour

### Documents modifies
- docs/04_devops/B3_2_ci_frontend_workflow.md
- docs/04_devops/DEVOPS.md
- docs/00_onboarding/ONBOARDING.md
- docs/05_decisions/DECISIONS_LOG.md
- docs/03_frontend/FRONTEND.md

### Resume documentaire
Fiche creee avant implementation, puis finalisee en fin de tache.

## 13) Definition of Done

- Code implemente et teste : Oui
- Documentation mise a jour : Oui
- Cle Jira referencee dans docs : Oui
- Onboarding possible sans oral : Oui

## 14) Trace de decision

- Reference Decision Log : docs/05_decisions/DECISIONS_LOG.md
- Action(s) de suivi : activer les required status checks frontend dans la protection de branche (B.3.3).

## 15) Changelog tache

- v1 : creation de la fiche avant implementation.
- v2 : implementation workflow CI frontend + tests Vitest smoke + validation locale + mise a jour documentation.

## 16) Texte Jira pret a coller

- Resume court de la decision : B.3.2 implemente avec un workflow frontend aligne sur le backend (jobs separes lint, test, build).
- Impact principal : regressions frontend detectees automatiquement avant merge et artefact frontend publie en CI.
- Documentation associee : docs/04_devops/B3_2_ci_frontend_workflow.md
- Point d'attention pour le prochain ticket : activer les checks `CI Frontend / Lint (ESLint)`, `CI Frontend / Unit Tests (Vitest)` et `CI Frontend / Build (Vite)` dans B.3.3.
