# Decision Log (ADR simplifie)

Consigner chaque decision technique significative.

## Template d'entree

### Decision YYYY-MM-DD - [CLE_JIRA]

#### Contexte
_Pourquoi cette decision est necessaire et dans quel cadre elle s'applique._

#### Decision retenue
_Choix final valide et perimetre exact._

#### Alternatives considerees
_Options ecartees et raisons du rejet._

#### Compromis
_Arbitrages acceptes (performance, securite, complexite, maintenance)._ 

#### Impacts
- **Court terme :**
- **Long terme :**

#### Actions de suivi
- **Action 1 :**
- **Action 2 :**

#### Liens
- **Jira :**
- **Documentation associee :**
- **PR/Commit (si disponible) :**

## Entrees

### Decision 2026-04-10 - A.1.1 / A.1.2

#### Contexte
Definir un contrat commun pour les reponses API avant de demarrer les endpoints metier.

#### Decision retenue
Adopter un contrat ApiResponse simple pour le MVP (`success`, `message`, `data`, `errorCode`) et repousser la gestion detaillee des erreurs a une tache distincte.

#### Alternatives considerees
- Contrat minimal.
- Contrat enrichi avec details/traceId.
- Gestion d'erreurs complete des le debut.

#### Compromis
Moins de richesse initiale cote debug, mais demarrage plus rapide et contrat plus stable pour le frontend.

#### Impacts
- **Court terme :** simplifie l'implementation des premiers endpoints et aligne le parsing frontend.
- **Long terme :** la gestion des erreurs devra etre enrichie dans une tache dediee sans casser le contrat de base.

#### Actions de suivi
- **Action 1 :** documenter le format exact dans la fiche de tache.
- **Action 2 :** adresser la cartographie d'erreurs dans A.1.2.

#### Liens
- **Jira :** A.1.1 / A.1.2
- **Documentation associee :** docs/02_backend/A1_1_api_response_standard.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.1.2

#### Contexte
Definir une gestion d'erreurs exploitable pour le MVP sans complexifier le contrat ApiResponse de base.

#### Decision retenue
Adopter une cartographie HTTP -> errorCode stable pour le MVP avec payload d'erreur simple (`success=false`, `message`, `errorCode`, `data=null`), puis traiter les details champ par champ dans une iteration ulterieure.

#### Alternatives considerees
- Gestion enrichie immediate avec `errors[]` et `traceId`.
- Gestion minimale sans `errorCode` stable.

#### Compromis
Moins de precision immediatement pour les erreurs de validation complexes, mais integration frontend plus rapide et previsible.

#### Impacts
- **Court terme :** handlers frontend simplifies et comportement d'erreur coherent sur les endpoints critiques.
- **Long terme :** extension possible vers un format enrichi sans rupture de la structure de base.

#### Actions de suivi
- **Action 1 :** implementer `RestControllerAdvice` avec mapping MVP.
- **Action 2 :** ouvrir un ticket d'enrichissement (`errors[]`, `path`, `traceId`) apres les features critiques.

#### Liens
- **Jira :** A.1.2
- **Documentation associee :** docs/02_backend/A1_2_error_mapping_strategy.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.2.1

#### Contexte
Definir des conventions DTO stables avant les premieres implementations metier pour eviter l'exposition des entites JPA.

#### Decision retenue
Appliquer une separation stricte Entity/DTO avec usage exclusif de `*Request` et `*Response` dans les controllers, mapping explicite via mappers dedies, et interdiction de champs sensibles en sortie.

#### Alternatives considerees
- Exposition partielle des entites.
- Mapping implicite dans controllers.
- DTO hybrides non distingues en entree/sortie.

#### Compromis
Plus de classes a maintenir, mais couplage reduit et evolution API plus sure.

#### Impacts
- **Court terme :** surcharge initiale de structure, mais meilleure lisibilite des contrats API.
- **Long terme :** reduction des regressions et facilitation des versions API sans rupture brutale.

#### Actions de suivi
- **Action 1 :** formaliser les conventions dans la fiche A.2.1.
- **Action 2 :** definir la politique de validation A.2.2.

#### Liens
- **Jira :** A.2.1
- **Documentation associee :** docs/02_backend/A2_1_dto_request_response_conventions.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.2.2

#### Contexte
Definir une politique de validation coherente entre backend et frontend afin d'eviter les ecarts de comportement sur les formulaires et les endpoints.

#### Decision retenue
Mettre en place une validation en double niveau :
- **Frontend :** validation immediate des formulaires avec schemas explicites.
- **Backend :** validation autoritative sur les DTO `*Request` via Bean Validation.

Les regles metier critiques sont toujours verifiees cote backend, meme si elles existent deja cote frontend.

#### Alternatives considerees
- Validation uniquement frontend (rejetee pour raisons de securite et fiabilite).
- Validation uniquement backend (rejetee pour UX degradee).
- Validation divergente par equipe/composant (rejetee pour maintenance difficile).

#### Compromis
Duplication partielle des regles entre frontend et backend, mais meilleure experience utilisateur et robustesse fonctionnelle.

#### Impacts
- **Court terme :** besoin de formaliser les regles de validation et messages d'erreur dans les deux couches.
- **Long terme :** reduction des bugs de saisie, coherence des reponses d'erreur, maintenance plus previsible.

#### Actions de suivi
- **Action 1 :** definir un socle minimal de regles communes (required, tailles, format email, pattern mots de passe).
- **Action 2 :** imposer l'usage de `@Valid` sur les endpoints d'entree backend.
- **Action 3 :** aligner les messages d'erreur frontend avec les `errorCode` backend.

#### Liens
- **Jira :** A.2.2
- **Documentation associee :** docs/02_backend/A2_2_validation_policy.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.3.1

#### Contexte
Definir une Definition of Done backend commune pour verrouiller le niveau de qualite avant les premieres implementations metier.

#### Decision retenue
Adopter une DoD backend binaire, obligatoire avant passage en "Done", couvrant architecture, contrat API, validation, securite, tests, qualite statique, documentation et exploitabilite minimale.

#### Alternatives considerees
- DoD legere limitee a "code compile".
- DoD informelle non ecrite.
- DoD uniquement centree sur tests sans documentation.

#### Compromis
Augmentation du temps de cloture par ticket, mais reduction forte des regressions et de la dette technique.

#### Impacts
- **Court terme :** discipline de livraison plus stricte, besoin de verification systematique avant merge.
- **Long terme :** meilleure stabilite du backend, onboarding facilite, maintenance plus previsible.

#### Actions de suivi
- **Action 1 :** publier la checklist DoD backend dans la fiche A.3.1.
- **Action 2 :** appliquer cette DoD sur les prochains tickets backend (auth, chat, erreurs).
- **Action 3 :** decliner une version equivalente pour frontend (A.3.2) et CI (A.3.3).

#### Liens
- **Jira :** A.3.1
- **Documentation associee :** docs/02_backend/A3_1_backend_definition_of_done.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.3.2

#### Contexte
Definir une Definition of Done frontend claire pour limiter les regressions UI, les ecarts de typage et les divergences d'integration API.

#### Decision retenue
Adopter une DoD frontend binaire, obligatoire avant passage en "Done", couvrant structure feature-based, typage strict, integration API, validation UX, securite, tests, qualite statique, documentation et verification manuelle minimale.

#### Alternatives considerees
- DoD frontend minimale limitee a build/lint.
- DoD non formalisee par checklist.
- DoD sans exigences de documentation ni verification manuelle.

#### Compromis
Un effort supplementaire de verification par ticket, mais une forte reduction des regressions et une meilleure lisibilite des livraisons frontend.

#### Impacts
- **Court terme :** davantage de discipline sur les PR frontend et meilleure coherence avec les contrats backend.
- **Long terme :** stabilite UI accrue, maintenance simplifiee, onboarding facilite.

#### Actions de suivi
- **Action 1 :** publier la checklist DoD frontend dans la fiche A.3.2.
- **Action 2 :** appliquer cette DoD aux prochains tickets frontend (auth, routes protegees, chat).
- **Action 3 :** aligner les templates PR frontend avec cette DoD.

#### Liens
- **Jira :** A.3.2
- **Documentation associee :** docs/03_frontend/A3_2_frontend_definition_of_done.md
- **PR/Commit (si disponible) :** a renseigner lors de l'implementation

### Decision 2026-04-10 - A.3.3

#### Contexte
Definir une Definition of Done CI/CD pour garantir que les PR soient validees automatiquement par des controles qualite reproductibles avant merge.

#### Decision retenue
Adopter une DoD CI/CD binaire avec pipelines backend/frontend automatiques sur PR/push, gates obligatoires (lint, tests, build), blocage du merge en cas d'echec, et documentation de reproduction locale.

#### Alternatives considerees
- CI partielle sans blocage de merge.
- CI limitee au build uniquement.
- Validation manuelle sans pipeline obligatoire.

#### Compromis
Temps de feedback CI potentiellement plus long, mais reduction forte des regressions en integration et standardisation des revues.

#### Impacts
- **Court terme :** exigences de qualite explicites sur toutes les PR et adaptation initiale des workflows.
- **Long terme :** fiabilite des livraisons, baisse des incidents post-merge, onboarding technique simplifie.

#### Actions de suivi
- **Action 1 :** finaliser les workflows CI backend/frontend selon les gates validees.
- **Action 2 :** verifier le blocage de merge si checks critiques rouges.
- **Action 3 :** documenter les commandes de reproduction locale des checks.

#### Liens
- **Jira** : A.3.3 (Phase 1), B.3.1/B.3.2 (Phase 2)
- **Documentation associée** : 
  - `docs/04_devops/A3_3_cicd_definition_of_done.md` (vue générale + phases)
  - `docs/04_devops/GitHub_Branch_Protection_Setup.md` (Phase 1 - IMPLÉMENTÉ)
  - `docs/04_devops/GitHub_Infrastructure_Plan.md` (Phase 2 - plan pour B.3.1/B.3.2)
- **Artefacts Phase 1** : `branch-ruleset.json` (mis à jour)
- **PR/Commit (si disponible)** : à renseigner lors du push infrastructure

### Decision 2026-04-10 - B.2.1

#### Contexte
Le scaffold Vite React TypeScript propose React 19 par defaut. La planification frontend mentionnait React 18 dans la documentation de conventions.

#### Decision retenue
Valider l'evolution de plan frontend vers React 19 pour B.2.1 et aligner le socle genere (runtime + types) sur cette version.

#### Alternatives considerees
- Forcer un downgrade en React 18 pour rester strictement sur l'ancienne convention.
- Conserver React 19 en code sans mettre a jour la documentation (rejete pour manque de clarte).

#### Compromis
Adoption rapide des versions recentes avec necessite de maintenir la compatibilite des bibliotheques de l'ecosysteme lors des prochaines taches.

#### Impacts
- **Court terme :** dependances frontend alignees sur React 19, lint/build verifies verts.
- **Long terme :** conventions frontend mises a jour et reduction des ecarts entre scaffold officiel et standards projet.

#### Actions de suivi
- **Action 1 :** verifier la compatibilite des dependances B.2.2 avec React 19.
- **Action 2 :** maintenir les docs de stack et onboarding synchronisees avec cette decision.

#### Liens
- **Jira :** B.2.1
- **Documentation associee :** docs/03_frontend/B2_1_react_vite_typescript_strict.md
- **PR/Commit (si disponible) :** a renseigner

### Decision 2026-04-10 - B.3.2

#### Contexte
La CI backend etait deja active, mais le frontend ne disposait pas encore de pipeline automatique lint/tests/build.

#### Decision retenue
Implementer un workflow CI Frontend distinct et structure comme le backend, avec trois jobs sequentiels :
- Lint (ESLint)
- Unit Tests (Vitest)
- Build (Vite)

Le workflow publie egalement l'artefact frontend `dist/`.

#### Alternatives considerees
- Pipeline frontend en job unique lint+test+build.
- Pipeline frontend sans tests unitaires.
- Validation uniquement locale sans workflow CI dedie.

#### Compromis
Pipeline un peu plus long qu'un job unique, mais meilleure lisibilite des echecs et alignement fort avec la strategie qualite backend.

#### Impacts
- **Court terme :** couverture CI frontend immediate avec verifications automatisees reproductibles localement.
- **Long terme :** reduction des regressions UI/types avant merge et facilitation de l'activation des required status checks en B.3.3.

#### Actions de suivi
- **Action 1 :** activer les checks frontend en requis dans `branch-ruleset.json` (B.3.3).
- **Action 2 :** enrichir progressivement les tests frontend au-dela du smoke test initial.

#### Liens
- **Jira :** B.3.2
- **Documentation associee :** docs/04_devops/B3_2_ci_frontend_workflow.md
- **PR/Commit (si disponible) :** a renseigner

### Decision 2026-04-15 - B.1.2

#### Contexte
Le backend ne demarrait pas en local hors tests, faute de datasource configuree. Le projet avait besoin d une strategie de donnees reproductible entre dev/test et compatible avec PostgreSQL cible.

#### Decision retenue
Mettre en place la strategie suivante:
- Profils Spring avec `dev` par defaut.
- Datasource PostgreSQL configuree via variables d environnement (`DB_URL`, `DB_USER`, `DB_PASSWORD`).
- PostgreSQL local standardise avec `docker-compose.yml`.
- Migrations SQL versionnees avec Flyway.
- Test integration Spring Boot execute sur PostgreSQL reel via Testcontainers.

#### Alternatives considerees
- Conserver H2 pour les tests integration (rejete: ecarts dialecte SQL).
- Utiliser Liquibase pour cette iteration (rejete: complexite superieure au besoin immediat).
- Installation PostgreSQL manuelle poste par poste (rejete: faible reproductibilite).

#### Compromis
Les tests integration sont un peu plus lents car ils lancent un conteneur, mais la fiabilite technique est meilleure et les ecarts dev/prod sont reduits.

#### Impacts
- **Court terme :** backend demarrable localement avec DB reelle, migration appliquee automatiquement, tests integration plus representatifs.
- **Long terme :** base solide pour B.1.3 puis Auth/Chat, evolution du schema maitrisable sans SQL ad hoc.

#### Actions de suivi
- **Action 1 :** implémenter B.1.3 (architecture en couches) sur ce socle.
- **Action 2 :** creer les entites/repositories metier (C.1.1) en s appuyant sur Flyway.
- **Action 3 :** ajouter ensuite les Dockerfiles backend/frontend (B.3.x) pour completer la chaine locale->CI.

#### Liens
- **Jira :** B.1.2
- **Documentation associee :** docs/02_backend/B1_2_postgresql_profiles_flyway_testcontainers.md
- **PR/Commit (si disponible) :** a renseigner

### Decision 2026-04-15 - B.1.3 + C.1.1/C.1.2/C.1.3 + C.2.1

#### Contexte
Le besoin etait de maximiser maintenabilite/testabilite du socle avant d'ajouter JWT, tout en disposant d'une fonctionnalite minimale pour valider l'integration MVP.

#### Decision retenue
Implementer une architecture backend en couches minimale et reutilisable, avec flux auth register/login fonctionnel sans JWT pour ce sprint:
- mot de passe hashé BCrypt,
- endpoints auth publics,
- reponses standardisees + gestion d'erreur globale,
- UI frontend minimale pour tester register/login.

#### Alternatives considerees
- Integrer JWT complet dans le meme sprint.
- Reporter toute auth apres architecture complete.
- Faire un prototype auth monolithique sans couches.

#### Compromis
Securite stateless incomplete a court terme (pas de token), mais dette reduite cote architecture, meilleure testabilite, et integration plus fiable.

#### Impacts
- **Court terme :** register/login utilisables pour valider le socle; CI locale backend/frontend verte.
- **Long terme :** base propre pour introduire JWT en sprint suivant sans refonte structurelle majeure.

#### Actions de suivi
- **Action 1 :** implementer C.1.4 (JWT + endpoints proteges stateless).
- **Action 2 :** etendre frontend auth avec etat global + intercepteur JWT (C.2.2/C.2.3).
- **Action 3 :** finaliser B.3.3 pour checks requis frontend en merge gate.

#### Liens
- **Jira :** B.1.3, C.1.1, C.1.2, C.1.3, C.2.1
- **Documentation associee :** docs/02_backend/B1_3_C1_auth_foundation_without_jwt.md ; docs/03_frontend/C2_1_auth_forms_minimal.md
- **PR/Commit (si disponible) :** a renseigner
