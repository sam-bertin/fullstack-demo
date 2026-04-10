# Fiche de Tache Jira - A.1.2 Definir la cartographie des erreurs

## 1) Identification
- Cle Jira: A.1.2
- Epic: EPIC A - Gouvernance Architecture et Qualite
- Fonctionnalite: A.1 - Contrat API et gestion des erreurs
- Tache: Definir la cartographie des erreurs
- Branche Git: a renseigner lors de l'implementation
- Pull Request: a renseigner lors de l'implementation
- Date: 2026-04-10
- Auteur: GitHub Copilot

## 2) Contexte et objectif
- Probleme a resoudre: sans cartographie commune, les erreurs REST deviennent incoherentes et difficiles a traiter cote frontend.
- Pourquoi cette tache est necessaire: fournir une base d'erreurs stable avant les features critiques (auth, chat, validation).
- Valeur utilisateur/technique attendue: comportement d'erreur previsible, messages lisibles, gestion frontend simplifiee.

## 3) Perimetre
- Inclus: mapping MVP HTTP -> errorCode, format de payload d'erreur simple, regles globales de traitement.
- Exclu: details d'erreur champ par champ avances, traceId, format enrichi complet.

## 4) Conception technique
- Solution retenue: cartographie stable avec payload d'erreur simple.
- Mapping retenu (MVP):
  - 400 -> VALIDATION_ERROR
  - 401 -> UNAUTHORIZED
  - 403 -> FORBIDDEN
  - 404 -> RESOURCE_NOT_FOUND
  - 409 -> CONFLICT
  - 500 -> INTERNAL_ERROR
- Payload d'erreur retenu:
  - success = false
  - message = texte lisible
  - data = null
  - errorCode = code stable
- Alternatives considerees: enrichissement immediat avec errors[] et traceId, gestion minimale sans errorCode stable.
- Pourquoi cette solution: meilleur compromis entre vitesse d'implementation MVP et robustesse d'integration frontend.
- Compromis (performance, securite, complexite, maintenance): moins de detail initial en erreur fine, mais meilleure coherence globale et evolutivite.

## 5) Implementation detaillee
- Fichiers modifies (liste): a renseigner lors de l'implementation.
- Changement par fichier: a renseigner lors de l'implementation.
- Contrats impactes (API, evenements, DB): contrat des erreurs REST, futur RestControllerAdvice.

## 6) Configuration et environnement
- Variables d'environnement ajoutees/modifiees: aucune.
- Valeurs d'exemple non sensibles: aucune.
- Prerequis specifiques: contrat A.1.1 valide.

## 7) Base de donnees
- Entites/tables modifiees: aucune.
- Migrations: aucune.
- Impact compatibilite: aucun impact schema direct.

## 8) API et evenements
- Endpoints ajoutes/modifies: aucun endpoint metier, mais impact global sur les reponses d'erreur.
- Payload request/response: normalisation des payloads d'erreur.
- Codes d'erreur et cas limites: mapping MVP explicite, cas enrichis reportes.
- Flux d'evenements (si applicable): non applicable.

## 9) Securite
- Risques identifies: exposition de messages trop verbeux, incoherence des codes HTTP, erreurs non maitrisees.
- Mesures appliquees: standardisation des erreurs et code errorCode stable.
- Verifications effectuees: revue du mapping et validation du compromis MVP.

## 10) Tests et validation
- Tests unitaires ajoutes/modifies: a ajouter lors de l'implementation du handler global.
- Tests integration/e2e: a ajouter pour verifier les statuts et payloads d'erreur.
- Resultats: non executes a ce stade.
- Procedure de verification manuelle: provoquer 400/401/404 sur endpoint de test et verifier la forme de reponse.

## 11) CI/CD et exploitation
- Workflows impactes: aucun immediatement.
- Build/deploiement impactes: aucun.
- Logs/monitoring/runbooks impactes: preparation du terrain pour une meilleure observabilite en phase durcissement.

## 12) Documentation mise a jour
- Documents modifies dans docs/: docs/05_decisions/DECISIONS_LOG.md, docs/02_backend/A1_2_error_mapping_strategy.md.
- Resume des modifications documentaires: decision sur la strategie d'erreurs MVP, mapping explicite, limites connues, trajectoire d'enrichissement.

## 13) Definition of Done
- Code implemente et teste: Non
- Documentation mise a jour: Oui
- Cle Jira referencee dans docs: Oui
- Onboarding possible sans oral: Oui

## 14) Trace de decision
- Reference Decision Log (docs/05_decisions/DECISIONS_LOG.md): entree du 2026-04-10 (A.1.2).
- Action(s) de suivi: implementer le RestControllerAdvice conforme a ce mapping, puis planifier un ticket d'enrichissement errors[]/traceId.

## 15) Changelog tache
- v1: Creation de la fiche de cadrage A.1.2 avec mapping MVP des erreurs.
- v2: Ajout du payload d'erreur cible et des limites de perimetre.
- v3: Reserve a la phase d'implementation.

## 16) Texte Jira pret a coller
- Resume court de la decision: Validation d'une cartographie d'erreurs MVP stable (HTTP -> errorCode) avec payload simple pour accelerer l'integration frontend.
- Impact principal: comportement d'erreur coherent sur les endpoints critiques, sans complexifier le contrat de base.
- Documentation associee: docs/05_decisions/DECISIONS_LOG.md et docs/02_backend/A1_2_error_mapping_strategy.md.
- Point d'attention pour le prochain ticket: enrichir ensuite vers un format detaille (errors[], path, traceId) sans rupture du contrat ApiResponse simple.
