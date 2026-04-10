# Import Jira - Mode operatoire exact

Ce dossier contient un import en 3 etapes pour respecter ta hierarchie:
- EPIC -> Fonctionnalite -> Tache

Fichiers:
- 01_epics.csv
- 02_features.csv
- 03_tasks.csv

## Avant de commencer

1. Cree un projet Jira (Scrum ou Kanban).
2. Verifie que ces types existent:
- Epic
- Story (utilise pour les Fonctionnalites)
- Sub-task (utilise pour les Taches)
3. Dans Jira, ouvre Settings -> Issues -> Issue types si besoin.

## Etape 1 - Importer les EPICs

1. Va dans Jira Settings -> System -> External system import -> CSV.
2. Selectionne jira-import/01_epics.csv.
3. Mappe les colonnes:
- Issue Type -> Issue Type
- Summary -> Summary
- Description -> Description
- Priority -> Priority
- Labels -> Labels
- Epic Name -> Epic Name
4. Lance l'import.
5. Note les cles creees, par exemple PROJ-1 a PROJ-5.

Astuce:
- Tu peux retrouver facilement les cles en filtrant sur type Epic dans le backlog.

## Etape 2 - Importer les Fonctionnalites (Stories)

Le fichier 02_features.csv contient des placeholders:
- <EPIC_A_KEY>
- <EPIC_B_KEY>
- <EPIC_C_KEY>
- <EPIC_D_KEY>
- <EPIC_E_KEY>

1. Ouvre 02_features.csv.
2. Remplace les placeholders par les vraies cles Epic creees a l'etape 1.
Exemple:
- <EPIC_A_KEY> -> PROJ-1
- <EPIC_B_KEY> -> PROJ-2
3. Sauvegarde.
4. Reviens dans Jira CSV import.
5. Mappe:
- Issue Type -> Issue Type
- Summary -> Summary
- Description -> Description
- Priority -> Priority
- Labels -> Labels
- Epic Link -> Epic Link
6. Lance l'import.
7. Recupere les cles des Stories creees.

## Etape 3 - Importer les Taches (Sub-tasks)

Le fichier 03_tasks.csv contient des placeholders de parent Story:
- <FEATURE_A1_KEY>
- <FEATURE_A2_KEY>
- etc.

1. Ouvre 03_tasks.csv.
2. Remplace chaque placeholder par la cle Story correspondante creee a l'etape 2.
Exemple:
- <FEATURE_A1_KEY> -> PROJ-6
- <FEATURE_B1_KEY> -> PROJ-9
3. Sauvegarde.
4. Lance l'import CSV.
5. Mappe:
- Issue Type -> Issue Type
- Summary -> Summary
- Description -> Description
- Priority -> Priority
- Labels -> Labels
- Parent -> Parent
6. Lance l'import.

## Verification finale

1. Dans le backlog:
- Tu vois 5 Epics.
- Chaque Epic contient ses Stories de Fonctionnalites.
- Chaque Story contient ses Sub-tasks.
2. Ouvre une Story et verifie:
- champ Epic Link renseigne.
- Sub-tasks attachees.
3. Lance un filtre rapide par label mvp pour verifier la couverture MVP.

## Si Jira refuse un mapping

1. Si Epic Link n'apparait pas:
- Active les champs Epic dans le projet.
- Verifie que le projet supporte Epic/Story.
2. Si Parent ne fonctionne pas:
- Verifie que le type est bien Sub-task (pas Task).
3. Si Priority ne passe pas:
- Aligne les valeurs sur les priorites exactes du projet (Highest, High, Medium).

## Option plus simple (sans import CSV)

Tu peux aussi creer les tickets manuellement en bulk create:
1. Cree d'abord les Epics.
2. Cree les Stories et lie-les aux Epics.
3. Cree les Sub-tasks depuis chaque Story.

L'import CSV reste le plus rapide pour ton volume actuel.
