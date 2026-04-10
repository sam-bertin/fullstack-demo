# Fiche de Tache Jira - A.2.1 Definir conventions DTO Request/Response

## 1) Identification
- Cle Jira: A.2.1
- Epic: EPIC A - Gouvernance Architecture et Qualite
- Fonctionnalite: A.2 - Contrat DTO et validation
- Tache: Definir conventions DTO Request/Response
- Branche Git: a renseigner lors de l'implementation
- Pull Request: a renseigner lors de l'implementation
- Date: 2026-04-10
- Auteur: GitHub Copilot

## 2) Contexte et objectif
- Probleme a resoudre: sans conventions DTO, les controllers risquent d'exposer des entites de persistence et de melanger les responsabilites.
- Pourquoi cette tache est necessaire: stabiliser les contrats API et proteger le domaine backend des couplages externes.
- Valeur utilisateur/technique attendue: API claire, evolutive et securisee, avec un perimetre de donnees controle.

## 3) Perimetre
- Inclus: conventions de nommage `*Request` / `*Response`, separation stricte Entity/DTO, principes de mapping.
- Exclu: implementation complete de tous les mappers metier et validation detaillee (A.2.2).

## 4) Conception technique
- Solution retenue:
  - Les controllers n'acceptent/retournent que des DTO.
  - Les entites JPA restent internes a la couche service/repository.
  - Les DTO d'entree et de sortie sont separes (`CreateXRequest`, `XResponse`, etc.).
  - Les champs sensibles ne sortent jamais dans les DTO de reponse.
  - Le mapping Entity <-> DTO est realise dans des mappers dedies, pas dans les controllers.
- Alternatives considerees: exposition d'entites JPA, mapping ad hoc dans controllers, DTO non differencies.
- Pourquoi cette solution: meilleure maitrise du contrat API et moindre couplage avec la persistence.
- Compromis (performance, securite, complexite, maintenance): plus de classes et de mapping, mais securite et maintenabilite largement ameliorees.

## 5) Implementation detaillee
- Fichiers modifies (liste): a renseigner lors de l'implementation.
- Changement par fichier: a renseigner lors de l'implementation.
- Contrats impactes (API, evenements, DB): contrats API REST des endpoints backend.

## 6) Configuration et environnement
- Variables d'environnement ajoutees/modifiees: aucune.
- Valeurs d'exemple non sensibles: aucune.
- Prerequis specifiques: A.1.1 et A.1.2 valides.

## 7) Base de donnees
- Entites/tables modifiees: aucune a ce stade (decision de conventions).
- Migrations: aucune.
- Impact compatibilite: diminution des risques d'exposition du schema interne.

## 8) API et evenements
- Endpoints ajoutes/modifies: aucun endpoint metier a ce stade.
- Payload request/response: standardisation des conventions de forme.
- Codes d'erreur et cas limites: aligns sur A.1.2 pour les erreurs.
- Flux d'evenements (si applicable): non applicable.

## 9) Securite
- Risques identifies: fuite de champs sensibles (password, metadata interne), couplage excessif au modele DB.
- Mesures appliquees: DTO de sortie controles, separation stricte des couches.
- Verifications effectuees: revue des conventions et des anti-patterns a eviter.

## 10) Tests et validation
- Tests unitaires ajoutes/modifies: a ajouter lors de l'implementation des mappers/services.
- Tests integration/e2e: a ajouter lors des premiers endpoints.
- Resultats: non executes a ce stade.
- Procedure de verification manuelle: verifier qu'aucun endpoint de test ne retourne une entite JPA brute.

## 11) CI/CD et exploitation
- Workflows impactes: aucun immediatement.
- Build/deploiement impactes: aucun.
- Logs/monitoring/runbooks impactes: impact indirect via meilleure stabilite des contrats API.

## 12) Documentation mise a jour
- Documents modifies dans docs/: docs/05_decisions/DECISIONS_LOG.md, docs/02_backend/A2_1_dto_request_response_conventions.md.
- Resume des modifications documentaires: decision formalisee sur conventions DTO et regles de separation des couches.

## 13) Definition of Done
- Code implemente et teste: Non
- Documentation mise a jour: Oui
- Cle Jira referencee dans docs: Oui
- Onboarding possible sans oral: Oui

## 14) Trace de decision
- Reference Decision Log (docs/05_decisions/DECISIONS_LOG.md): entree du 2026-04-10 (A.2.1).
- Action(s) de suivi: definir la politique de validation A.2.2 puis appliquer les conventions sur les premiers endpoints backend.

## 15) Changelog tache
- v1: Creation de la fiche de cadrage A.2.1.
- v2: Formalisation des regles de separation Entity/DTO et des conventions de nommage.
- v3: Reserve a la phase d'implementation.

## 16) Texte Jira pret a coller
- Resume court de la decision: validation d'une separation stricte Entity/DTO avec conventions `*Request`/`*Response` et mapping dedie.
- Impact principal: reduction du couplage avec la persistence et meilleure stabilite des contrats API.
- Documentation associee: docs/05_decisions/DECISIONS_LOG.md et docs/02_backend/A2_1_dto_request_response_conventions.md.
- Point d'attention pour le prochain ticket: A.2.2 definira les regles de validation frontend/backend appliquees a ces DTO.
