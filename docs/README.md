# Documentation Technique

Ce dossier contient la documentation exhaustive du projet.

References externes:
- Jira: https://reptar.atlassian.net/jira/software/c/projects/FUL/boards/3
- GitHub: https://github.com/sam-bertin/fullstack-demo

Objectifs:
- Permettre l'onboarding sans transmission orale.
- Expliquer ce qui a ete fait, pourquoi, et comment le reproduire.
- Tracer les decisions techniques et leur impact.

Workflow recommande:
1. Discussion et validation des choix.
2. Redaction de la decision dans `docs/05_decisions/DECISIONS_LOG.md` si le choix a un impact d'architecture ou de maintenance.
3. Creation ou mise a jour de la fiche tache dans `docs/TASK_DOC_TEMPLATE.md`.
4. Implementation seulement apres validation.
5. Synthese finale et texte Jira pret a coller.

Structure:
- `00_onboarding/` : prerequis, installation, premier demarrage, depannage.
- `01_architecture/` : vues systeme, conventions, diagrammes, flux.
- `02_backend/` : modules, endpoints, securite, validation, persistence.
- `03_frontend/` : architecture features, flux UI, etat, appels API.
- `04_devops/` : CI/CD, Docker, workflow Git, checks qualite.
- `05_decisions/` : ADR et journal des compromis.
- `06_runbooks/` : procedures operationnelles.

Template operationnel:
- `TASK_DOC_TEMPLATE.md` : fiche standard Jira -> documentation a remplir pour chaque tache implementee.

Regle de fonctionnement:
- L'utilisateur ne remplit pas les fiches a la main: l'agent produit la documentation, puis propose un resume Jira reutilisable.

Regle de maintenance:
- Chaque tache Jira implementee doit mettre a jour au moins un document dans `docs/`.
- Chaque mise a jour documentaire doit mentionner la cle Jira concernee.
