# API tests - Bruno

Collection API partagee pour valider les endpoints auth sans passer par l'UI.

## Prerequis
- Backend demarre sur `http://localhost:8080`
- Docker actif si vous utilisez PostgreSQL local compose
- Bruno Desktop (optionnel) ou Bruno CLI

## Structure
- `bruno.json` : metadonnees collection
- `environments/local.bru` : variables locales
- `environments/ci.bru` : variables CI
- `auth/*.bru` : scenarios API auth

## Scenarios
1. `01_register_success.bru` : register (201) ou deja existant (409)
2. `02_login_success.bru` : login nominal (200)
3. `03_login_invalid_password.bru` : login KO (401)
4. `04_register_validation_error.bru` : validation KO (400)

## Execution locale (CLI)
```powershell
# Option 1: depuis la racine du repo
npx @usebruno/cli run ./api-tests/auth --env local

# Option 2: depuis le dossier api-tests
cd api-tests
npx @usebruno/cli run ./auth --env local

# Option 3: installation globale
npm install -g @usebruno/cli
bru run ./api-tests/auth --env local
```

### Execution locale robuste (evite les collisions utilisateur)
Si un utilisateur de test existe deja en base avec un ancien mot de passe, le scenario login peut echouer en 401.
Pour garantir un run vert avant CI, lancer la collection avec des variables uniques:

```powershell
cd api-tests
$suffix = Get-Date -Format "yyyyMMddHHmmss"
npx @usebruno/cli run ./auth --env local `
	--env-var authEmail=bruno.auth.$suffix@example.com `
	--env-var authUsername=brunouser$suffix `
	--env-var authPass=testpass1 `
	--env-var invalidPass=testpass2
```

## Notes
- Le scenario register est idempotent: il accepte 201 (nouvel utilisateur) ou 409 (utilisateur deja cree).
- L'utilisateur de test est configure dans les fichiers d'environnement Bruno.
