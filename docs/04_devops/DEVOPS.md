# DevOps

## CI/CD
- Workflows GitHub Actions backend/frontend.
- Gates qualite: lint, tests, build.

## Docker
- Dockerfile backend multi-stage.
- Dockerfile frontend multi-stage + nginx.
- Orchestration locale (compose).

## Workflow Git
- Branching model.
- Conventional commits.
- PR vers main uniquement.
- Templates PR separes: `.github/PULL_REQUEST_TEMPLATE/backend.md` et `.github/PULL_REQUEST_TEMPLATE/frontend.md`.

## Verification release locale
- Commandes exactes.
- Resultats attendus.

## Gouvernance outillage (obligatoire)
- Toute installation ou mise a jour d'outil impactant le build/test doit etre documentee au fil de l'eau.
- La source de verite pour l'installation locale est `docs/00_onboarding/ONBOARDING.md`.
- Chaque entree doit inclure:
	- Date
	- Cle Jira associee
	- Outil/version
	- Chemin binaire (pattern portable, sans chemin personnel complet)
	- Commandes de verification
	- Resultat attendu et resultat observe

### Commandes minimales de verification outillage backend
- `java -version` -> doit retourner Java 21.x
- `javac -version` -> doit retourner Java 21.x
- `mvn -v` -> doit retourner Maven 3.9+ et Java 21.x
- `where.exe java`
- `where.exe javac`
- `where.exe mvn`

## Trace Jira
- Ticket(s): a renseigner.
