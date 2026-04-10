# Feuille de Route Produit et Technique

Ce document est la source de verite du plan projet.

Objectif:
- Livrer un MVP de chat 1-to-1 en temps reel.
- Montrer la maitrise Java Spring, React, API REST, evenements, base de donnees, CI/CD, Docker et pratiques qualite.

Strategie:
- Construire un MVP vertical rapidement.
- Integrer des garde-fous d'architecture et de qualite des le debut.
- Iterer ensuite sans exploser la dette technique.

Format Jira:
- EPIC -> Fonctionnalite -> Tache.

Definition TCA appliquee a chaque tache:
- Pourquoi: raison metier/technique de la tache.
- Livrables: ce qui doit exister en sortie.
- Criteres d'acceptation: conditions objectives de validation.
- Dependances: prerequis avant execution.
- Risques couverts: risques reduits par la tache.

---

## EPIC A - Gouvernance Architecture et Qualite

Description:
Poser les regles communes backend/frontend/CI avant les fonctionnalites critiques.

Pourquoi cet epic:
- Eviter les divergences de conventions.
- Reduire le cout d'integration entre backend et frontend.
- Creer un cadre de qualite stable pour toutes les PR.

### Fonctionnalite A.1 - Contrat API et gestion des erreurs

Description:
Definir un langage commun pour les reponses API et les erreurs.

#### Tache A.1.1 - Definir le contrat ApiResponse standard

Pourquoi:
- Unifier les reponses pour simplifier le parsing frontend et les tests.

Livrables:
- Modele de reponse standardise (success, message, data, errorCode).
- Exemples succes et erreur pour endpoints REST.

Criteres d'acceptation:
- 100% des nouveaux endpoints REST suivent le format standard.

Dependances:
- Aucune.

Risques couverts:
- Reponses heterogenes difficiles a maintenir.

#### Tache A.1.2 - Definir la cartographie des erreurs

Pourquoi:
- Garantir des statuts HTTP coherents et une UX claire.

Livrables:
- Table exception -> statut HTTP -> code erreur -> message.
- Regles de mapping pour RestControllerAdvice.

Criteres d'acceptation:
- Les erreurs critiques (auth, validation, ressource absente, technique) sont mappées.

Dependances:
- A.1.1.

Risques couverts:
- Erreurs opaques et comportement non previsible de l'API.

### Fonctionnalite A.2 - Contrat DTO et validation

Description:
Definir des regles strictes de structure des donnees entrantes et sortantes.

#### Tache A.2.1 - Definir conventions DTO Request/Response

Pourquoi:
- Decoupler API publique et modele de persistence.

Livrables:
- Conventions de nommage Request/Response.
- Regles de mapping entite -> DTO.

Criteres d'acceptation:
- Aucune entite JPA exposee directement par les controllers.

Dependances:
- Aucune.

Risques couverts:
- Fuite du schema de base de donnees via API.

#### Tache A.2.2 - Definir politique de validation backend/frontend

Pourquoi:
- Rejeter les donnees invalides au plus tot et de facon uniforme.

Livrables:
- Regles backend via annotations de validation.
- Regles frontend via schemas de validation.

Criteres d'acceptation:
- Les formulaires auth ont validation frontend + backend coherente.

Dependances:
- A.2.1.

Risques couverts:
- Incoherence des validations et bugs de donnees.

### Fonctionnalite A.3 - Definition of Done (DoD)

Description:
Formaliser le seuil minimal de qualite avant merge.

#### Tache A.3.1 - Definir DoD Backend

Pourquoi:
- Eviter la baisse de qualite dans les couches metier critiques.

Livrables:
- Checklist backend: architecture, tests, lint, logs, documentation minimale.

Criteres d'acceptation:
- Toute PR backend reference explicitement la checklist.

Dependances:
- Aucune.

Risques couverts:
- Regressions silencieuses et dette technique rapide.

#### Tache A.3.2 - Definir DoD Frontend

Pourquoi:
- Stabiliser l'UX et le typage strict TypeScript.

Livrables:
- Checklist frontend: typage strict, tests critiques, accessibilite de base.

Criteres d'acceptation:
- Aucune PR frontend avec erreurs lint/typing.

Dependances:
- Aucune.

Risques couverts:
- Instabilite UI et dette de typage.

#### Tache A.3.3 - Definir DoD CI/CD

Pourquoi:
- Rendre la qualite executable et automatique.

Livrables:
- Liste des checks obligatoires de merge.

Criteres d'acceptation:
- Merge bloque si les checks critiques sont rouges.

Dependances:
- Aucune.

Risques couverts:
- Merges instables et cassures frequentes.

---

## EPIC B - Fondations Techniques

Description:
Creer un socle executable localement et verifiable en CI.

Pourquoi cet epic:
- Acceleration des developpements suivants.
- Reduction des erreurs d'environnement.

### Fonctionnalite B.1 - Initialisation Backend Spring Boot

Description:
Mettre en place le squelette backend conforme aux standards du projet.

#### Tache B.1.1 - Initialiser le projet Spring Boot

Pourquoi:
- Demarrer avec une base technique propre et complete.

Livrables:
- Projet Maven Java 21.
- Dependances: Spring Web, Spring Data JPA, Validation, Security, WebSocket, PostgreSQL Driver, Lombok.

Criteres d'acceptation:
- Le projet compile et les tests de base s'executent.

Dependances:
- A.* recommande.

Risques couverts:
- Setup incomplet et retards sur les features.

#### Tache B.1.2 - Configurer PostgreSQL et profils

Pourquoi:
- Isoler la configuration par environnement et proteger les secrets.

Livrables:
- Configuration profils dev/test.
- Variables sensibles externalisees.

Criteres d'acceptation:
- Demarrage applicatif valide avec connexion base.

Dependances:
- B.1.1.

Risques couverts:
- Fuites de secrets et ecarts dev/prod.

#### Tache B.1.3 - Mettre en place l'architecture en couches

Pourquoi:
- Maintenir une separation claire des responsabilites.

Livrables:
- Packages: controller, service, serviceimpl, repository, dto, config, exception.

Criteres d'acceptation:
- Exemple fonctionnel traversant controller -> service -> repository.

Dependances:
- B.1.1.

Risques couverts:
- Couplage fort et difficultes de test.

### Fonctionnalite B.2 - Initialisation Frontend React

Description:
Mettre en place un socle frontend modulaire par features.

#### Tache B.2.1 - Initialiser React Vite TypeScript strict

Pourquoi:
- Controler la qualite de typage des le debut.

Livrables:
- Application Vite React TS avec mode strict active.

Criteres d'acceptation:
- Build et lint passent sans avertissement critique.

Dependances:
- Aucune.

Risques couverts:
- Erreurs runtime dues a un typage faible.

#### Tache B.2.2 - Installer les dependances coeur frontend

Pourquoi:
- Eviter des refactors structurels tardifs.

Livrables:
- react-router-dom, axios, @tanstack/react-query, zustand, react-hook-form, zod.

Criteres d'acceptation:
- Dependencies verrouillees et importables.

Dependances:
- B.2.1.

Risques couverts:
- Instabilite due a des choix tardifs d'outillage.

#### Tache B.2.3 - Structurer le frontend par features

Pourquoi:
- Faciliter l'evolution de nouvelles fonctionnalites.

Livrables:
- Structure initiale: features/auth, features/chat, shared/lib, shared/ui.

Criteres d'acceptation:
- Une feature exemple respecte les conventions.

Dependances:
- B.2.1.

Risques couverts:
- Dispersion du code et maintenance difficile.

### Fonctionnalite B.3 - CI qualite de base

Description:
Mettre des controles automatiques obligatoires a chaque PR.

#### Tache B.3.1 - Creer workflow CI backend

Pourquoi:
- Detecter rapidement regressions serveur.

Livrables:
- Workflow backend: lint/checkstyle, tests unitaires, build.

Criteres d'acceptation:
- Workflow vert sur PR backend.

Dependances:
- B.1.1.

Risques couverts:
- Regressions backend introduites en silence.

#### Tache B.3.2 - Creer workflow CI frontend

Pourquoi:
- Bloquer les regressions UI/types avant merge.

Livrables:
- Workflow frontend: lint, tests, build.

Criteres d'acceptation:
- Workflow vert sur PR frontend.

Dependances:
- B.2.1.

Risques couverts:
- Instabilite frontend en production.

#### Tache B.3.3 - Definir gate de qualite de merge

Pourquoi:
- Rendre la discipline de qualite non contournable.

Livrables:
- Protection PR avec checks requis.

Criteres d'acceptation:
- Merge impossible si check requis echoue.

Dependances:
- B.3.1, B.3.2.

Risques couverts:
- Contournement des controles qualite.

---

## EPIC C - MVP Authentification

Description:
Permettre inscription + connexion securisees avec JWT.

Pourquoi cet epic:
- Toute fonctionnalite de chat prive depend d'une identite fiable.

### Fonctionnalite C.1 - Auth backend JWT

Description:
Construire les endpoints et la logique metier d'authentification.

#### Tache C.1.1 - Creer le modele User

Pourquoi:
- Supporter la gestion d'identite applicative.

Livrables:
- Entite User, repository, contrainte unicite email.

Criteres d'acceptation:
- Creation/lecture utilisateur valides via tests.

Dependances:
- B.1.2.

Risques couverts:
- Incoherence de base d'identite.

#### Tache C.1.2 - Implementer register

Pourquoi:
- Permettre la creation securisee de comptes.

Livrables:
- Endpoint register + service + hash BCrypt + validation.

Criteres d'acceptation:
- Le mot de passe n'est jamais stocke en clair.

Dependances:
- C.1.1, A.2.2.

Risques couverts:
- Faille de securite mot de passe.

#### Tache C.1.3 - Implementer login et generation JWT

Pourquoi:
- Authentifier sans session serveur, compatible scalabilite.

Livrables:
- Endpoint login, generation JWT, reponse standardisee.

Criteres d'acceptation:
- Identifiants invalides renvoient erreur standard.

Dependances:
- C.1.1, A.1.1.

Risques couverts:
- Authentification fragile et comportement incoherent.

#### Tache C.1.4 - Securiser les endpoints REST

Pourquoi:
- Proteger les ressources privees.

Livrables:
- Configuration Security, filtre JWT, regles publiques/privees.

Criteres d'acceptation:
- Endpoint protege inaccessible sans token valide.

Dependances:
- C.1.3.

Risques couverts:
- Acces non autorise aux donnees.

### Fonctionnalite C.2 - Auth frontend

Description:
Construire le parcours utilisateur d'authentification cote client.

#### Tache C.2.1 - Creer formulaires login/register

Pourquoi:
- Offrir une entree utilisateur claire et validee.

Livrables:
- Pages login/register avec react-hook-form + zod.

Criteres d'acceptation:
- Messages de validation explicites et actionnables.

Dependances:
- B.2.2, C.1.2, C.1.3.

Risques couverts:
- Mauvaise UX et erreurs de saisie.

#### Tache C.2.2 - Gerer l'etat auth

Pourquoi:
- Centraliser l'identite connectee dans le frontend.

Livrables:
- Store Zustand pour token + utilisateur.

Criteres d'acceptation:
- Etat auth coherent apres navigation et refresh selon regle choisie.

Dependances:
- C.2.1.

Risques couverts:
- Sessions incoherentes et bugs de navigation.

#### Tache C.2.3 - Configurer client API avec intercepteur JWT

Pourquoi:
- Eviter la duplication de logique d'auth sur chaque appel.

Livrables:
- Client Axios centralise, injection Authorization, gestion 401/403.

Criteres d'acceptation:
- Les appels prives incluent automatiquement le token.

Dependances:
- C.2.2.

Risques couverts:
- Oubli d'authentification sur certains appels.

#### Tache C.2.4 - Mettre en place les routes protegees

Pourquoi:
- Controler l'acces UI selon l'etat de session.

Livrables:
- Guard de route + redirection login.

Criteres d'acceptation:
- Acces chat bloque pour utilisateur non authentifie.

Dependances:
- C.2.2.

Risques couverts:
- Fuite d'ecrans prives.

---

## EPIC D - MVP Chat 1-to-1 Temps Reel

Description:
Permettre l'envoi et la reception de messages instantanes entre deux utilisateurs.

Pourquoi cet epic:
- C'est la valeur metier centrale du produit et la preuve de maitrise des evenements.

### Fonctionnalite D.1 - Chat backend

Description:
Mettre en place l'infrastructure WebSocket, la securite et la persistence.

#### Tache D.1.1 - Configurer WebSocket et broker

Pourquoi:
- Fournir un canal temps reel bidirectionnel.

Livrables:
- Endpoint handshake, destinations applicatives et topics.

Criteres d'acceptation:
- Connexion WebSocket etablie depuis client authentifie.

Dependances:
- B.1.1, C.1.4.

Risques couverts:
- Latence elevee d'un modele REST seul.

#### Tache D.1.2 - Implementer routage 1-to-1

Pourquoi:
- Garantir que chaque message arrive uniquement au destinataire attendu.

Livrables:
- Logique de routage par utilisateur cible.

Criteres d'acceptation:
- Message visible uniquement par emetteur et destinataire.

Dependances:
- D.1.1.

Risques couverts:
- Fuite de messages a des tiers.

#### Tache D.1.3 - Securiser le handshake WebSocket

Pourquoi:
- Empêcher les connexions anonymes ou frauduleuses.

Livrables:
- Verification JWT a la connexion WebSocket.

Criteres d'acceptation:
- Handshake refuse sans token valide.

Dependances:
- C.1.3, D.1.1.

Risques couverts:
- Utilisation abusive du canal temps reel.

#### Tache D.1.4 - Persister l'historique de messages

Pourquoi:
- Retrouver les conversations apres reconnexion.

Livrables:
- Entite message, repository, service, endpoint historique.

Criteres d'acceptation:
- Historique correctement trie et recuperable par conversation.

Dependances:
- D.1.2, B.1.2.

Risques couverts:
- Perte d'information conversationnelle.

### Fonctionnalite D.2 - Chat frontend

Description:
Creer l'interface de chat et la logique de communication temps reel.

#### Tache D.2.1 - Integrer client WebSocket robuste

Pourquoi:
- Assurer une bonne experience meme en reseau instable.

Livrables:
- Connexion, reconnexion, gestion d'erreurs et etat de connexion.

Criteres d'acceptation:
- Reconnexion automatique apres coupure reseau courte.

Dependances:
- D.1.1, D.1.3.

Risques couverts:
- Deconnexions frequentes sans reprise.

#### Tache D.2.2 - Construire la page de chat

Pourquoi:
- Offrir une interface lisible et operationnelle pour les echanges.

Livrables:
- Liste contacts, fil de discussion, champ de saisie.

Criteres d'acceptation:
- Interface exploitable sur desktop et mobile.

Dependances:
- C.2.4.

Risques couverts:
- UI confuse et faible adoption.

#### Tache D.2.3 - Implementer envoi/reception en direct

Pourquoi:
- Realiser la promesse temps reel du produit.

Livrables:
- Hook/chat service frontend, mise a jour immediate de la conversation.

Criteres d'acceptation:
- Message recu quasi instantanement entre deux sessions.

Dependances:
- D.2.1, D.2.2, D.1.2.

Risques couverts:
- Experience degradee et comportement incoherent.

#### Tache D.2.4 - Charger l'historique initial

Pourquoi:
- Assurer la continuite de lecture de conversation.

Livrables:
- Chargement REST a l'ouverture d'une conversation, puis continuation via WebSocket.

Criteres d'acceptation:
- Historique visible avant reception des nouveaux messages.

Dependances:
- D.1.4, D.2.2.

Risques couverts:
- Conversations vides apres recharge de page.

---

## EPIC E - Durcissement MVP et Preparation des Iterations

Description:
Fiabiliser le MVP avant d'ajouter des fonctionnalites avancées.

Pourquoi cet epic:
- Iterer sur un socle fragile augmente fortement le cout de correction.

### Fonctionnalite E.1 - Securite operationnelle

#### Tache E.1.1 - Finaliser CORS et politiques d'acces

Pourquoi:
- Reduire la surface d'attaque navigateur.

Livrables:
- Whitelist d'origines explicites.

Criteres d'acceptation:
- Origine non autorisee bloquee systematiquement.

Dependances:
- C.1.4.

Risques couverts:
- Exposition non voulue des endpoints.

#### Tache E.1.2 - Hygiene secrets et configuration

Pourquoi:
- Eviter les fuites de credentials et tokens.

Livrables:
- Secrets externalises, templates d'environnement, controles CI.

Criteres d'acceptation:
- Aucun secret hardcode dans le depot.

Dependances:
- B.1.2, B.3.3.

Risques couverts:
- Compromission de l'application par fuite de secrets.

### Fonctionnalite E.2 - Observabilite

#### Tache E.2.1 - Mettre en place des logs structures

Pourquoi:
- Accelerer le diagnostic en cas d'incident.

Livrables:
- Format de logs coherent backend/frontend + correlation minimale.

Criteres d'acceptation:
- Une erreur utilisateur est traçable de bout en bout.

Dependances:
- D.2.3.

Risques couverts:
- Temps de resolution long des incidents.

### Fonctionnalite E.3 - Qualite et tests critiques

#### Tache E.3.1 - Ajouter tests backend critiques

Pourquoi:
- Proteger la logique metier la plus sensible.

Livrables:
- Tests auth/chat au niveau service.

Criteres d'acceptation:
- Scenarios nominaux + erreurs couverts.

Dependances:
- C.*, D.1.*.

Risques couverts:
- Regressions backend apres evolution.

#### Tache E.3.2 - Ajouter tests frontend critiques

Pourquoi:
- Stabiliser les parcours utilisateurs principaux.

Livrables:
- Tests de composants/hooks auth/chat.

Criteres d'acceptation:
- Scenarios login et reception message verifies.

Dependances:
- C.2.*, D.2.*.

Risques couverts:
- Regressions UI non detectees.

#### Tache E.3.3 - Validation E2E manuelle MVP

Pourquoi:
- Confirmer la coherence globale du produit.

Livrables:
- Script manuel reproductible register -> login -> chat -> historique.

Criteres d'acceptation:
- Parcours complet valide sur deux comptes test.

Dependances:
- C.*, D.*, E.1.*.

Risques couverts:
- Incoherences inter-couches non vues en tests unitaires.

### Fonctionnalite E.4 - Conteneurisation locale

Description:
Standardiser le demarrage local et preparer la portabilite CI/CD.

#### Tache E.4.1 - Dockerfile backend multi-stage

Pourquoi:
- Construire une image runtime legere et reproductible.

Livrables:
- Dockerfile backend builder -> runner.

Criteres d'acceptation:
- Backend demarrable en conteneur avec configuration externe.

Dependances:
- B.1.*, C.*, D.1.*.

Risques couverts:
- Ecarts d'execution entre postes dev.

#### Tache E.4.2 - Dockerfile frontend multi-stage

Pourquoi:
- Servir une build statique propre via serveur web.

Livrables:
- Dockerfile frontend avec build puis Nginx.

Criteres d'acceptation:
- Frontend servi correctement en conteneur.

Dependances:
- B.2.*, C.2.*, D.2.*.

Risques couverts:
- Deploiement frontend non reproductible.

#### Tache E.4.3 - Orchestration locale via compose

Pourquoi:
- Lancer tout l'environnement avec une commande.

Livrables:
- Orchestration frontend + backend + postgres.

Criteres d'acceptation:
- Stack complete operationnelle depuis un point d'entree unique.

Dependances:
- E.4.1, E.4.2, B.1.2.

Risques couverts:
- Integration locale lente et sujette aux erreurs manuelles.

---

## Sequence recommandee

1. Priorite haute: EPIC A (contrats + DoD).
2. Priorite haute: EPIC B (socles backend/frontend/CI).
3. Priorite moyenne-haute: EPIC C (auth complete).
4. Priorite moyenne-haute: EPIC D (chat realtime + historique).
5. Priorite moyenne: EPIC E (durcissement avant extension).

---

## Hors MVP (iteratifs)

- Chat de groupe.
- Pieces jointes fichiers/images.
- Presence avancee et statuts enrichis.
- Notifications push.
- Chiffrement de bout en bout.

---

## Regle d'evolution du plan

Toute nouvelle fonctionnalite doit inclure:
- Pourquoi on la fait.
- Livrables attendus.
- Criteres d'acceptation mesurables.
- Dependances.
- Risques couverts.

Regle de priorisation:
- Prioriser d'abord la valeur utilisateur et la reduction de risque technique.
