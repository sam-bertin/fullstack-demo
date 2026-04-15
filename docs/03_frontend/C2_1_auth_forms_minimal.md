# Fiche de Tache Jira - C.2.1 Formulaires login/register (UI minimale)

## 1) Identification

### Metadonnees
- **Cle Jira :** C.2.1
- **Epic :** EPIC C - MVP Authentification
- **Fonctionnalite :** Auth frontend
- **Tache :** Créer une UI minimale login/register pour valider le socle backend
- **Date :** 2026-04-15
- **Auteur :** GitHub Copilot

### Liens de travail
- **Branche Git :** a renseigner
- **Pull Request :** a renseigner

## 2) Contexte et objectif

### Probleme
Le frontend ne dispose pas encore de flux auth permettant de valider concrètement register/login côté backend.

### Justification
Une UI minimale réduit la friction de validation manuelle du socle et accélère les itérations d'intégration.

### Valeur attendue
- Ecrans register/login fonctionnels.
- Validation formulaire côté client.
- Appels API auth centralisés.

## 3) Perimetre

### Inclus
- UI minimale login/register.
- Validation client.
- Intégration API backend auth.

### Exclu
- Gestion état auth globale persistante.
- Protection routes par token JWT.

## 4) Conception technique

### Solution retenue
- Composants fonctionnels organisés par feature `auth`.
- Hook dédié aux appels auth.
- Contrats de types frontend alignés backend.

### Alternatives considerees
- Implémentation directe dans `App.tsx` sans structure feature.

### Justification du choix
Prépare l'extensibilité sans retarder excessivement le sprint.

### Compromis
- **Performance :** impact faible.
- **Securite :** pas de gestion token ce sprint.
- **Complexite :** structure légère mais non finale.
- **Maintenance :** meilleure qu'un prototype monolithique.

## 5) Implementation detaillee

### Fichiers modifies
- `frontend/package.json`
- `frontend/src/App.tsx`
- `frontend/src/App.css`
- `frontend/src/index.css`
- `frontend/src/shared/lib/apiClient.ts`
- `frontend/src/features/auth/types/auth.ts`
- `frontend/src/features/auth/schemas/authSchemas.ts`
- `frontend/src/features/auth/hooks/useAuthApi.ts`
- `frontend/src/features/auth/components/AuthPage.tsx`

### Changement par fichier
- Ajout d'un client API Axios centralisé et des contrats TS alignés backend.
- Ajout des schémas Zod + formulaires React Hook Form pour register/login.
- Remplacement de la page Vite par une UI auth minimale orientée test du socle.

### Contrats impactes
- **API :** `/api/v1/auth/register`, `/api/v1/auth/login`.
- **Evenements :** non applicable.
- **Base de donnees :** indirect via backend.

## 10) Tests et validation

### Tests
- **Unitaires :** a compléter.
- **Integration/E2E :** vérification manuelle du parcours.

### Resultats
- `npm run lint`: OK
- `npm run test`: OK
- `npm run build`: OK

### Verification manuelle
- La page expose bien les deux modes register/login.
- Les validations client empêchent les payloads invalides.
- Les retours backend sont affichés dans la zone de feedback.

## 16) Texte Jira pret a coller

- **Resume court de la decision :** ajout d'une UI auth minimale pour valider register/login du backend dans une logique de socle.
- **Impact principal :** testabilité intégration frontend-backend améliorée.
- **Documentation associee :** docs/03_frontend/C2_1_auth_forms_minimal.md.
- **Point d'attention pour le prochain ticket :** état auth global + JWT côté frontend.