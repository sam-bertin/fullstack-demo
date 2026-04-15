# Matrice de Statut des Taches (Status Dashboard)

Ce document centralise l'etat actuel d'avancement des taches par rapport au plan (`PROJECT_PLAN.md`). Il doit etre mis a jour a chaque cloture de ticket pour refleter la realite de ce qui est reellement implemente dans le code par rapport a ce qui est documente/prevu.

## Gouvernance et Architecture (EPIC A)

| Cle Jira | Composant | Description | Statut | Implementation Code | Documentation |
|---|---|---|---|---|---|
| A.1.1 | Backend | Contrat ApiResponse standard | ✅ Termine | Oui | Documente |
| A.1.2 | Backend | Cartographie des erreurs | ✅ Termine | Oui | Documente |
| A.2.1 | Backend | Conventions DTO Request/Response | 📝 Prevu | Non | Documente |
| A.2.2 | Fullstack | Politique de validation | 📝 Prevu | Non | Documente |
| A.3.1 | Backend | Definition of Done Backend | ✅ Termine | N/A | Documente |
| A.3.2 | Frontend | Definition of Done Frontend | ✅ Termine | N/A | Documente |
| A.3.3 | CI/CD | Definition of Done CI/CD | ✅ Phase 1 Terminee | Implémenté (Branch protection) | Documente |

## Fondations Techniques (EPIC B)

| Cle Jira | Composant | Description | Statut | Implementation Code | Documentation |
|---|---|---|---|---|---|
| B.1.1 | Backend | Initialisation Spring Boot | ✅ Bootstrapped | Oui | Documente |
| B.1.2 | Backend | Config PostgreSQL & profils | ✅ Termine | Oui | Documente |
| B.1.3 | Backend | Architecture en couches | ✅ Termine | Oui | Documente |
| B.2.1 | Frontend | React Vite TS Strict (React 19) | ✅ Bootstrapped | Oui | Documente |
| B.2.2 | Frontend | Dependances coeur | 🚧 Partiel | Partiel | A completer |
| B.2.3 | Frontend | Structure features | 📝 Prevu | Non | A documenter |
| B.3.1 | CI/CD | CI Backend | ✅ Termine | Oui (Push + Pull Request) | Documente |
| B.3.2 | CI/CD | CI Frontend | ✅ Termine | Oui (Push + Pull Request) | Documente |
| B.3.3 | CI/CD | Gate de qualite merge | 🚧 Partiel | Oui (workflow QA + tests Bruno/Playwright) / Required checks frontend+QA en attente | Documente |

## MVC Authentification (EPIC C)

| Cle Jira | Composant | Description | Statut | Implementation Code | Documentation |
|---|---|---|---|---|---|
| C.1.1 | Backend | Creer le modele User | ✅ Termine | Oui | Incluse sprint |
| C.1.2 | Backend | Implementer register | ✅ Termine | Oui (conflits email/username -> 409) | Incluse sprint |
| C.1.3 | Backend | Implementer login et generation JWT | 🚧 Partiel | Login oui / JWT non | Incluse sprint |
| C.1.4 | Backend | Securiser les endpoints REST | 📝 Prevu | Non (JWT reporte) | A documenter |
| C.2.1 | Frontend | Creer formulaires login/register | ✅ Termine | Oui (tabs WAI-ARIA + clavier) | Documente |
| C.2.2 | Frontend | Gerer l'etat auth | 📝 Prevu | Non | A documenter |
| C.2.3 | Frontend | Configurer client API avec intercepteur JWT | 🚧 Partiel | Client API minimal sans JWT | A completer |

## Derniere mise a jour

- **2026-04-15 (review hardening) :**
	- Option A appliquee sur le switch auth frontend (pattern tabs WAI-ARIA complet + navigation clavier).
	- Logging serveur ajoute sur exceptions inattendues et mapping `DataIntegrityViolationException` vers `409 RESOURCE_CONFLICT`.
	- Verification explicite de l unicite username en register + tests unitaires/integration associes.
	- Passe warning cleanup: metadata Spring pour `app.cors.allowed-origins`, migration Testcontainers anti-deprecation, et correction des warnings JSX labels/inputs.
	- Option 1 appliquee: reduction du bruit runtime des tests backend via configuration Surefire Mockito (agent pre-attache) et logging test cible sur Testcontainers.
	- Hygiene CI API tests renforcee: variables Bruno renommees (`authPass`/`invalidPass`) et valeurs placeholders non sensibles pour limiter les faux positifs secret scanning/Sonar.
	- Ajout d une commande pre-push globale locale (`scripts/verify-prepush-full.ps1`) couvrant backend + API smoke Bruno + frontend verify pour aligner la verification locale sur la CI.

## MVP Chat (EPIC D) - *A venir*
- Non demarre

## Durcissement (EPIC E) - *A venir*
- Non demarre
