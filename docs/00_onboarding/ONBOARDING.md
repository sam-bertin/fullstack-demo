# Onboarding Projet

## Objectif
Permettre a un nouveau developpeur de lancer l'application sans aide externe.

## Prerequis
- Java 21
- Maven 3.9+
- Node.js LTS
- npm ou pnpm
- Docker + Docker Compose (recommande)
- PostgreSQL local (si pas de Docker)

## Variables d'environnement
- Documenter ici toutes les variables backend/frontend avec exemples.
- Indiquer lesquelles sont obligatoires, optionnelles, et leur valeur par defaut.

## Premier demarrage (local)
1. Cloner le repository.
2. Configurer les variables d'environnement.
3. Demarrer la base de donnees.
4. Lancer backend puis frontend.
5. Verifier l'acces a l'application et aux endpoints de sante.

## Verification rapide
- Backend: demarrage OK, tests OK.
- Frontend: build OK, page chargee.
- End-to-end minimal: login puis acces ecran protege.

## Depannage rapide
- Ports occupes.
- Erreurs de connexion DB.
- Erreurs de CORS.
- Erreurs de token JWT.

## Trace Jira
- Ticket(s): a renseigner pour chaque mise a jour.
