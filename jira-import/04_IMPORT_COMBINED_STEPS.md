# Import Jira combine (Stories + Sub-tasks)

Utilise ce mode si Jira exige un mapping de "ID du ticket" pour gerer les parents.

Fichier:
- 04_features_tasks_combined.csv

## Pre-requis

1. Les epics existent deja (FUL-1 a FUL-5 dans ce fichier).
2. Les types Story et Sub-task existent dans ton projet.

## Mapping exact

Mappe les colonnes CSV comme suit:

1. Issue ID -> ID du ticket
2. Issue Type -> Type du ticket
3. Summary -> Summary
4. Description -> Description
5. Priority -> Priority
6. Labels -> Labels
7. Epic Link -> Epic Link
8. Parent ID -> Ticket parent

## Important

1. Ne mappe PAS Parent ID vers un type de lien (blocks/relates to/etc.).
2. Active "Mapper la valeur de champ" uniquement si Jira ne reconnait pas les valeurs de Issue Type ou Priority.
3. Les lignes Story ont Parent ID vide.
4. Les lignes Sub-task ont Parent ID = Issue ID de la Story parent.

## Validation rapide

1. Lance un import test avec 5 lignes (2 stories + 3 sub-tasks).
2. Verifie qu'une Sub-task est bien attachee a sa Story.
3. Si c'est bon, importe le fichier complet.

## Si erreur persiste

1. Verifie que le champ mappe a Ticket parent attend bien un ID de ticket CSV (pas une cle Jira).
2. Verifie que Sub-task est reconnu comme sous-type valide dans ton projet.
3. Verifie que les epics FUL-1..FUL-5 existent et sont accessibles.
