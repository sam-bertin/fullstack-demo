# Fiche de Tache Jira - A.3.1 Definir DoD Backend

## 1) Identification

### Metadonnees
- **Cle Jira :** A.3.1
- **Epic :** EPIC A - Gouvernance Architecture et Qualite
- **Fonctionnalite :** A.3 - Definition of Done (DoD)
- **Tache :** Definir DoD Backend
- **Date :** 2026-04-10
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner lors de l'implementation
- **Pull Request :** a renseigner lors de l'implementation

## 2) Contexte et objectif

### Probleme
Sans DoD explicite, la qualite backend varie selon les tickets et les merges peuvent introduire de la dette technique silencieuse.

### Justification
Une DoD backend commune fixe un seuil de qualite minimal objectif avant cloture d'une tache.

### Valeur attendue
- Homogeneite de livraison.
- Regressions reduites.
- Documentation et code alignes.

## 3) Perimetre

### Inclus
- Checklist de validation backend obligatoire avant passage en Done.
- Criteres binaires de validation.
- Regles de verification avant merge.

### Exclu
- DoD frontend (A.3.2).
- DoD CI/CD globale (A.3.3).

## 4) Conception technique

### Solution retenue
DoD backend composee des blocs suivants:

1. **Architecture**
- Respect Controller -> Service -> ServiceImpl -> Repository.
- Aucun retour d'entite JPA directe en API.

2. **Contrat API**
- Reponses conformes A.1.1.
- Erreurs conformes A.1.2.

3. **Validation**
- DTO `*Request` valides en entree.
- Rejets invalides au format d'erreur attendu.

4. **Securite**
- Endpoints proteges verifies.
- Aucune fuite de donnees sensibles.

5. **Tests**
- Tests service mis a jour sur les cas impactes.
- Cas nominal + au moins un cas d'erreur.

6. **Qualite statique**
- Build vert.
- Lint/checkstyle vert.

7. **Documentation**
- Fiche tache mise a jour.
- Decision log complete si choix significatif.
- Texte Jira pret a coller fourni.

8. **Exploitabilite minimale**
- Logs utiles sur erreurs critiques.
- Variables env documentees si modifiees.

### Alternatives considerees
- DoD minimale compile-only.
- DoD informelle non documentee.
- DoD sans exigence documentaire.

### Justification du choix
Cette DoD couvre les risques techniques majeurs du backend sans attendre les phases avancees du projet.

### Compromis
- **Performance :** aucun impact runtime direct.
- **Securite :** meilleure couverture des risques de fuite et d'acces non autorise.
- **Complexite :** effort de verification supplementaire a chaque ticket.
- **Maintenance :** fortement amelioree par la standardisation.

## 5) Implementation detaillee

### Fichiers modifies
- a renseigner lors de l'implementation

### Changement par fichier
- a renseigner lors de l'implementation

### Contrats impactes
- **API :** stabilite des livraisons backend.
- **Evenements :** indirect.
- **Base de donnees :** indirect via validations et tests.

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
- **Compatibilite :** meilleure fiabilite globale des evolutions.

## 8) API et evenements

### API
- **Endpoints ajoutes/modifies :** aucun endpoint metier a ce stade.
- **Payload request/response :** pas de changement de format, mais exigence de conformite.
- **Codes d'erreur et cas limites :** conformite A.1.2 obligatoire.

### Evenements
- **Flux d'evenements (si applicable) :** non applicable.

## 9) Securite

### Analyse
- **Risques identifies :** merges incomplets, validations absentes, exposition de donnees sensibles.
- **Mesures appliquees :** checklist de validation obligatoire avant Done.
- **Verifications effectuees :** revue transversale des points critiques backend.

## 10) Tests et validation

### Tests
- **Unitaires :** exigence de couverture des cas impactes.
- **Integration/E2E :** a completer selon criticite ticket.

### Resultats
- Non executes a ce stade (phase de cadrage).

### Verification manuelle
- S'assurer que tous les points DoD sont coches avant cloture de ticket.

## 11) CI/CD et exploitation

### Impacts pipeline
- **Workflows impactes :** verification de statut backend avant merge.
- **Build/deploiement impactes :** aucun changement de process, mais seuil d'acceptation plus strict.

### Exploitation
- **Logs/monitoring/runbooks impactes :** meilleure preparabilite operationnelle.

## 12) Documentation mise a jour

### Documents modifies
- docs/05_decisions/DECISIONS_LOG.md
- docs/02_backend/A3_1_backend_definition_of_done.md

### Resume documentaire
- DoD backend formalisee en checklist actionnable et criteres binaires de validation.

## 13) Definition of Done

- **Code implemente et teste :** Non
- **Documentation mise a jour :** Oui
- **Cle Jira referencee dans docs :** Oui
- **Onboarding possible sans oral :** Oui

## 14) Trace de decision

- **Reference Decision Log :** docs/05_decisions/DECISIONS_LOG.md (entree A.3.1)
- **Action(s) de suivi :** decliner la DoD frontend (A.3.2) et CI (A.3.3).

## 15) Changelog tache

- **v1 :** Creation de la fiche de cadrage A.3.1.
- **v2 :** Formalisation de la checklist DoD backend et des criteres binaires.
- **v3 :** Reserve a la phase d'implementation.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** validation d'une DoD backend binaire couvrant architecture, contrat API, validation, securite, tests, qualite statique, documentation et exploitabilite minimale.
- **Impact principal :** reduction des regressions et meilleur alignement code/tests/docs avant passage en Done.
- **Documentation associee :** docs/05_decisions/DECISIONS_LOG.md et docs/02_backend/A3_1_backend_definition_of_done.md.
- **Point d'attention pour le prochain ticket :** appliquer la meme rigueur sur A.3.2 (DoD Frontend) puis A.3.3 (DoD CI/CD).
