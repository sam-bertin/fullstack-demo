# Architecture

## Vue d'ensemble
- Description globale de l'application distribuee.
- Frontend SPA React + Backend Spring Boot + PostgreSQL.

## Couches backend
- Controller -> Service (interface) -> ServiceImpl -> Repository.
- Gestion erreurs via RestControllerAdvice.

## Architecture frontend
- Organisation feature-based.
- Separation logique (hooks/services) et presentation (components).

## Flux principaux
- Authentification (register/login/JWT).
- Chat temps reel (WebSocket) + persistence.

## Contrats transverses
- Format de reponse API.
- Politique validation.
- Codes d'erreur standardises.

## Diagrammes
- Ajouter diagrammes C4/sequence si necessaire.

## Trace Jira
- Ticket(s): a renseigner.
