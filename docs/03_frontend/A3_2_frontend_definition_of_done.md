# Fiche de Tache Jira - A.3.2 Definir DoD Frontend

## 1) Identification

### Metadonnees
- **Cle Jira :** A.3.2
- **Epic :** EPIC A - Gouvernance Architecture et Qualite
- **Fonctionnalite :** A.3 - Definition of Done (DoD)
- **Tache :** Definir DoD Frontend
- **Date :** 2026-04-10
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner lors de l'implementation
- **Pull Request :** a renseigner lors de l'implementation

## 2) Contexte et objectif

### Probleme
Sans DoD frontend explicite, les tickets peuvent etre considers termines malgre des regressions UI, des ecarts de typage ou des integrations API fragiles.

### Justification
Une DoD frontend commune fixe un seuil de qualite objectif avant cloture des taches et limite la dette UX/technique.

### Valeur attendue
- Cohérence des livraisons frontend.
- Diminution des regressions visuelles et fonctionnelles.
- Alignement durable avec les contrats backend.

## 3) Perimetre

### Inclus
- Checklist frontend obligatoire avant passage en Done.
- Criteres binaires de validation sur architecture, typing, integration, tests et doc.
- Verification manuelle minimale du parcours impacte.

### Exclu
- DoD backend (A.3.1).
- DoD CI/CD globale (A.3.3).

## 4) Conception technique

### Solution retenue
DoD frontend composee des blocs suivants:

1. **Architecture et structure**
- Structure feature-based respectee.
- Composants fonctionnels uniquement.
- Separation logique/UI respectee (hooks/services vs presentation).

2. **Typage**
- TypeScript strict respecte.
- Aucun `any` introduit sans justification validee.
- Types de payload API explicites sur les flux impactes.

3. **Integration API**
- Appels via client HTTP centralise.
- Contrat `ApiResponse` respecte.
- Gestion des erreurs `401`, `403`, `VALIDATION_ERROR`, `INTERNAL_ERROR` sur les flux impactes.

4. **Validation UX**
- Validation des formulaires impactes via schemas explicites.
- Messages d'erreur lisibles et actionnables.
- Coherence frontend/backend sur les regles critiques.

5. **Securite frontend**
- Donnees sensibles non exposees inutilement dans UI/state/storage.
- Routes protegees verifiees sur les zones impactees.
- Session coherente sur navigation/refresh.

6. **Tests**
- **Phase bootstrap (etat actuel)**: smoke baseline obligatoire (Vitest vert).
- **Phase features (prochaine etape)**: tests composants/hooks mis a jour avec au moins un cas nominal et un cas d'erreur UI.

7. **Qualite statique**
- Lint frontend vert.
- Build frontend vert.
- CI frontend verte.
- En bootstrap, le niveau minimum exige: lint + smoke test + build.

8. **Documentation**
- Fiche tache docs a jour.
- Decision log a jour si choix technique.
- Texte Jira pret a coller fourni.

9. **Verification manuelle minimale**
- Scenario utilisateur impacte rejoue de bout en bout.
- Resultat consigne dans la fiche.

### Alternatives considerees
- DoD frontend minimale limitee a lint/build.
- DoD informelle non ecrite.
- DoD sans exigences de tests ni de documentation.

### Justification du choix
La checklist retenue couvre les zones de risque critiques du frontend sans attendre les phases de durcissement final.

### Compromis
- **Performance :** aucun impact runtime direct.
- **Securite :** meilleure prevention des fuites et des acces UI incoherents.
- **Complexite :** effort de revue supplementaire sur chaque ticket frontend.
- **Maintenance :** nettement amelioree via standards communs.

## 5) Implementation detaillee

### Fichiers modifies
- a renseigner lors de l'implementation

### Changement par fichier
- a renseigner lors de l'implementation

### Contrats impactes
- **API :** robustesse de la consommation frontend des contrats backend.
- **Evenements :** coherence des flux realtime cote UI.
- **Base de donnees :** impact indirect via qualite des donnees soumises.

## 6) Configuration et environnement

### Variables d'environnement
- **Ajoutees/modifiees :** aucune
- **Valeurs d'exemple non sensibles :** aucune

### Prerequis
- A.1.1, A.1.2, A.2.1, A.2.2 valides.

## 7) Base de donnees

### Impacts schema
- **Entites/tables :** aucun changement direct.
- **Migrations :** aucune.
- **Compatibilite :** meilleure qualite d'entree via validation UI coherent.

## 8) API et evenements

### API
- **Endpoints ajoutes/modifies :** aucun endpoint metier a ce stade.
- **Payload request/response :** pas de changement de format, exigence de conformite frontend.
- **Codes d'erreur et cas limites :** gestion explicite des erreurs standard.

### Evenements
- **Flux d'evenements (si applicable) :** verification des flux impactes en cas de realtime.

## 9) Securite

### Analyse
- **Risques identifies :** routes exposees, erreurs non gerees, fuite de donnees en state/UI.
- **Mesures appliquees :** checklist DoD frontend avant Done.
- **Verifications effectuees :** revue des points critiques frontend.

## 10) Tests et validation

### Tests
- **Unitaires :** composants/hooks impactes.
- **Integration/E2E :** a completer selon criticite ticket.

### Resultats
- Non executes a ce stade (phase de cadrage).

### Critere minimum avant auth/chat (C.* / D.*)
- `npm run lint` vert.
- `npm run test` vert (au moins la smoke baseline).
- `npm run build` vert.
- La smoke baseline seule n'est plus suffisante des qu'une feature metier est livree.

### Verification manuelle
- Rejouer le scenario principal impacte et verifier les cas d'erreur associes.

## 11) CI/CD et exploitation

### Impacts pipeline
- **Workflows impactes :** verification CI frontend avant merge.
- **Build/deploiement impactes :** aucun changement de process, exigence de statut vert maintenue.

### Exploitation
- **Logs/monitoring/runbooks impactes :** meilleure qualite de diagnostic UI.

## 12) Documentation mise a jour

### Documents modifies
- docs/05_decisions/DECISIONS_LOG.md
- docs/03_frontend/A3_2_frontend_definition_of_done.md

### Resume documentaire
- DoD frontend formalisee en checklist actionnable et criteres binaires de validation.

## 13) Definition of Done

- **Code implemente et teste :** Non
- **Documentation mise a jour :** Oui
- **Cle Jira referencee dans docs :** Oui
- **Onboarding possible sans oral :** Oui

## 14) Trace de decision

- **Reference Decision Log :** docs/05_decisions/DECISIONS_LOG.md (entree A.3.2)
- **Action(s) de suivi :** decliner cette logique pour la DoD CI/CD (A.3.3).

## 15) Changelog tache

- **v1 :** Creation de la fiche de cadrage A.3.2.
- **v2 :** Formalisation de la checklist DoD frontend et criteres binaires.
- **v3 :** Reserve a la phase d'implementation.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** validation d'une DoD frontend binaire couvrant architecture, typage, integration API, validation UX, securite, tests, qualite statique, documentation et verification manuelle.
- **Impact principal :** reduction des regressions UI et meilleur alignement frontend/backend avant passage en Done.
- **Documentation associee :** docs/05_decisions/DECISIONS_LOG.md et docs/03_frontend/A3_2_frontend_definition_of_done.md.
- **Point d'attention pour le prochain ticket :** finaliser A.3.3 pour harmoniser la DoD au niveau CI/CD.
