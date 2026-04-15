# Fiche de Tache Jira - A.3.3 Definir DoD CI/CD

## 1) Identification

### Metadonnees
- **Cle Jira :** A.3.3
- **Epic :** EPIC A - Gouvernance Architecture et Qualite
- **Fonctionnalite :** A.3 - Definition of Done (DoD)
- **Tache :** Definir DoD CI/CD
- **Date :** 2026-04-10
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner lors de l'implementation
- **Pull Request :** a renseigner lors de l'implementation

## 2) Contexte et objectif

### Probleme
Sans DoD CI/CD explicite, une PR peut etre mergee malgre des controles incomplets ou non reproductibles, ce qui augmente les regressions post-merge.

### Justification
Une DoD CI/CD commune impose un seuil automatique de qualite et fiabilise l'integration continue entre backend et frontend.

### Valeur attendue
- Garde-fous techniques objectifs avant merge.
- Reproductibilite des checks en local et en CI.
- Reduction des erreurs detectees tardivement.

## 3) Perimetre

### Inclus
- Pipelines backend/frontend automatiques sur PR et push.
- Gates qualite obligatoires : lint, tests unitaires, build.
- Echec d'une gate => pipeline rouge.
- Blocage de merge si checks critiques en echec.
- Documentation des pre-requis et commandes de reproduction locale.

### Exclu
- Strategie complete de deploiement production.
- Monitoring avance de pipeline (SLO, dashboards dedies).

## 4) Conception technique

### Solution retenue
DoD CI/CD composee des blocs suivants:

1. **Declenchement**
- Workflows executes automatiquement sur PR et push.

2. **Gates qualite minimales**
- Lint.
- Tests unitaires.
- Build.

3. **Politique d'echec**
- Toute gate critique en echec rend le statut global rouge.
- Merge bloque tant que les checks requis ne sont pas verts.

4. **Reproductibilite**
- Versions d'outillage explicites (Java, Node).
- Caches dependances configures.
- Commandes locales documentees.

5. **Exploitabilite**
- Logs de job lisibles et diagnosticables.
- Etape fautive identifiable rapidement.

6. **Verification de fiabilite**
- Test de blocage merge sur PR volontairement en echec.
- Test de reprise apres correction.

### Alternatives considerees
- CI partielle non bloquante.
- Build-only sans tests/lint.
- Validation manuelle sans checks automatiques.

### Justification du choix
Ce niveau est suffisant pour un MVP robuste et pose les bases d'une qualite continue sans surcharger la phase de demarrage.

### Compromis
- **Performance :** duree CI parfois plus longue.
- **Securite :** meilleure maitrise des regressions avant merge.
- **Complexite :** effort initial de parametrage workflows + branch protections.
- **Maintenance :** stable une fois les checks standardises.

## 5) Implementation detaillee

### Fichiers modifies
- a renseigner lors de l'implementation

### Changement par fichier
- a renseigner lors de l'implementation

### Contrats impactes
- **API :** indirect via validation systematique des builds/tests.
- **Evenements :** indirect.
- **Base de donnees :** indirect.

## 6) Configuration et environnement

### Variables d'environnement
- **Ajoutees/modifiees :** a renseigner si necessaire
- **Valeurs d'exemple non sensibles :** a renseigner si necessaire

### Prerequis
- Workflows GitHub Actions backend/frontend existants.
- Regles de protection de branche actives pour checks requis.

## 7) Base de donnees

### Impacts schema
- **Entites/tables :** aucun impact direct.
- **Migrations :** non applicable.
- **Compatibilite :** indirectement amelioree par la qualite des builds/tests.

## 8) API et evenements

### API
- **Endpoints ajoutes/modifies :** aucun endpoint metier.
- **Payload request/response :** non applicable.
- **Codes d'erreur et cas limites :** non applicable.

### Evenements
- **Flux d'evenements (si applicable) :** non applicable.

## 9) Securite

### Analyse
- **Risques identifies :** merge de code non teste, checks contournables, divergences local/CI.
- **Mesures appliquees :** gates obligatoires + blocage merge + reproduction locale documentee.
- **Verifications effectuees :** definition des tests de validation de la pipeline.

## 10) Tests et validation

### Tests
- **Unitaires :** executes dans pipeline.
- **Integration/E2E :** hors perimetre minimal, a etendre selon maturite.

### Resultats
- Non executes a ce stade (phase de cadrage).

### Verification manuelle
- Lancer une PR en echec volontaire (lint/test/build) et verifier blocage.
- Corriger puis verifier retour au statut vert.

## 11) CI/CD et exploitation

### Impacts pipeline
- **Workflows impactes :** ci-backend.yml, ci-frontend.yml.
- **Build/deploiement impactes :** stabilisation du pre-merge.

### Exploitation
- **Logs/monitoring/runbooks impactes :** lisibilite des erreurs de pipeline amelioree.

## 12) Documentation mise a jour

### Documents modifies
- docs/05_decisions/DECISIONS_LOG.md
- docs/04_devops/A3_3_cicd_definition_of_done.md

### Resume documentaire
- DoD CI/CD formalisee avec gates minimales, regles de merge et exigences de reproductibilite.

## 13) Definition of Done

- **Code implemente et teste :** Non
- **Documentation mise a jour :** Oui
- **Cle Jira referencee dans docs :** Oui
- **Onboarding possible sans oral :** Oui

## 14) Trace de decision

- **Reference Decision Log :** docs/05_decisions/DECISIONS_LOG.md (entree A.3.3)
- **Action(s) de suivi :** appliquer la DoD dans les workflows et branch protections, puis verifier le comportement reel en PR.

## 15) Changelog tache

- **v1 :** Creation de la fiche de cadrage A.3.3.
- **v2 :** Formalisation des gates CI minimales et des criteres binaires.
- **v3 :** Reserve a la phase d'implementation.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** validation d'une DoD CI/CD binaire avec pipelines backend/frontend automatiques, gates lint/tests/build et blocage merge en cas d'echec.
- **Impact principal :** reduction des regressions post-merge et meilleure reproductibilite des controles qualite.
- **Documentation associee :** docs/05_decisions/DECISIONS_LOG.md et docs/04_devops/A3_3_cicd_definition_of_done.md.
- **Point d'attention pour le prochain ticket :** verifier techniquement les branch protections et l'execution reelle des checks requis sur PR.

## 17) Implementation technique realisee (etat mis a jour)

### Etat actuel

La DoD CI/CD A.3.3 est en place de facon incrementale:

#### Phase 1 : Protection de branche ✅ IMPLEMANTEE
- **Fichier** : `branch-ruleset.json`
- **Configuration** :
  - Suppression de `main` interdite
  - PR obligatoire
  - 1 approbateur minimum (self-review possible)
  - Resolution des threads obligatoire
  - Required status checks backend actifs

**Documentation** : `docs/04_devops/GitHub_Branch_Protection_Setup.md`

#### Phase 2 : Workflows CI + status checks 🚧 PARTIELLEMENT IMPLEMENTEE
- **Workflows actifs** :
  - `.github/workflows/ci-backend.yml` (push + pull_request)
  - `.github/workflows/ci-frontend.yml` (push + pull_request)
  - `.github/workflows/ci-qa.yml` (API smoke Bruno + E2E Playwright)
- **Required status checks actifs dans branch-ruleset** : backend uniquement
- **Restant a faire** : activer checks frontend et QA en required status checks pour blocage merge complet.

**Documentation detaillee** : `docs/04_devops/B3_3_api_bruno_playwright_quality_gates.md`

---

### Verifications effectuees

- `branch-ruleset.json` valide (JSON + regles PR/checks backend)
- CI backend active (lint/test/build)
- CI frontend active (lint/test/build)
- CI QA active (Bruno + Playwright)
- Reproduction locale documentee dans `docs/04_devops/DEVOPS.md` et `docs/00_onboarding/ONBOARDING.md`

---

### Etat de la DoD CI/CD

| Critere | Phase | Statut |
|---------|-------|--------|
| Pipelines backend/frontend automatiques | 2 | ✅ Backend + Frontend actifs |
| Gate QA API/E2E | 2 | ✅ Workflow QA actif |
| Gates lint/tests/build | 2 | ✅ Backend + Frontend actifs |
| Blocage merge si echec | 2 | 🚧 Backend actif ; frontend/QA a activer en required checks |
| Reproductibilite locale documentee | 2 | ✅ Documentee (backend/frontend/qa) |
| **Protection branche** | **1** | **✅ Implemente** |
| **PR obligatoire** | **1** | **✅ Implemente** |
| **Self-review minimum** | **1** | **✅ Implemente** |

**Conclusion** : A.3.3 est partiellement complete au niveau merge gate global. Les pipelines et tests sont en place ; la derniere etape est l'activation frontend/QA en required status checks sur `main`.
