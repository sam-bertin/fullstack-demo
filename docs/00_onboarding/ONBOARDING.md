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

## Installation outillage local (Windows + VS Code)

Objectif: standardiser le setup backend pour eviter les ecarts entre postes.

### Outils requis pour B.1.1
- VS Code
- Extensions Java/Spring (Java Extension Pack, Maven for Java, Java Test Runner, Spring Boot Dashboard)
- JDK 21
- Maven 3.9+

### Emplacements binaires a connaitre (patterns)
- JDK (si installe via gestionnaire JDK local):
	- `%USERPROFILE%\\.jdks\\openjdk-21.x.x\\bin\\java.exe`
	- `%JAVA_HOME%\\bin\\javac.exe`
- Maven (si installe via Scoop):
	- `%USERPROFILE%\\scoop\\apps\\maven\\current\\bin\\mvn.cmd`
- Maven (si installe via Chocolatey):
	- `%ProgramData%\\chocolatey\\lib\\maven\\apache-maven-3.x.x\\bin\\mvn.cmd`

### Variables d'environnement minimales
- `JAVA_HOME` doit pointer vers le dossier racine du JDK 21.
- `Path` doit contenir `%JAVA_HOME%\\bin`.
- `Path` doit contenir le dossier `bin` de Maven.

### Commandes de verification (a lancer dans un nouveau terminal)
1. Verifier Java runtime
	 - Commande: `java -version`
	 - Attendu: version `21.x`.

2. Verifier compilateur Java
	 - Commande: `javac -version`
	 - Attendu: version `21.x`.

3. Verifier JAVA_HOME
	 - Commande PowerShell: `$env:JAVA_HOME`
	 - Attendu: chemin vers un JDK 21.

4. Verifier Maven
	 - Commande: `mvn -v`
	 - Attendu:
		 - `Apache Maven 3.9.x` (ou plus)
		 - `Java version: 21.x`

5. Verifier resolution des bins
	 - Commandes: `where.exe java`, `where.exe javac`, `where.exe mvn`
	 - Attendu: au moins un chemin valide pour chaque binaire; les chemins JDK 21/Maven doivent apparaitre.

### Extensions VS Code a verifier
- Commande: `code --list-extensions`
- Attendu: presence au minimum de:
	- `redhat.java`
	- `vscjava.vscode-java-pack`
	- `vscjava.vscode-maven`
	- `vscjava.vscode-java-debug`
	- `vscjava.vscode-java-test`
	- `vmware.vscode-spring-boot`
	- `vscjava.vscode-spring-boot-dashboard`

### Checklist finale outillage backend
- Java 21 actif
- Maven 3.9+ actif
- JAVA_HOME configure
- Extensions Java/Spring presentes
- Verification commandes OK

## Prise en main des outils VS Code (Java/Spring)

Objectif: etre autonome sur les actions quotidiennes (run, debug, tests, build) sans IntelliJ.

### 1) Java Projects (navigation code)
- Ouvrir la vue `JAVA PROJECTS`.
- Verifier que le projet backend est detecte et indexe.
- Actions utiles:
	- Naviguer classes/packages.
	- Ouvrir rapidement la classe `Application`.
	- Verifier les references d'une classe/methode.

### 2) Spring Boot Dashboard (run/stop)
- Ouvrir la vue `SPRING BOOT DASHBOARD`.
- Verifier que l'application Spring apparait.
- Actions utiles:
	- Start: demarrer l'application.
	- Stop: arreter proprement.
	- Restart: redemarrer apres changement de config.
	- Open logs: consulter les logs de demarrage et erreurs.

### 3) Maven for Java (build lifecycle)
- Ouvrir la vue `MAVEN`.
- Cible standard backend:
	- `clean`
	- `test`
	- `package`
- Equivalent terminal:
	- `mvn clean test`
	- `mvn clean package`

### 4) Test Explorer (JUnit)
- Ouvrir la vue `TESTING`.
- Verifier que les tests JUnit sont detectes.
- Actions utiles:
	- Run test (unitaire)
	- Debug test (avec breakpoint)
	- Re-run failed tests

### 5) Debugger Java (breakpoints)
- Poser un breakpoint dans controller/service.
- Lancer `Run and Debug` sur l'application.
- Verifier en session debug:
	- Variables locales
	- Stack frames
	- Step over / step into

### 6) Routine quotidienne recommandee
1. Pull/rebase de la branche.
2. `mvn clean test`.
3. Start application via Spring Dashboard.
4. Verification endpoint de sante.
5. Avant commit: `mvn clean package`.

### 7) Depannage rapide cote editeur
- Si le projet Java n'apparait pas:
	- Commande palette: `Java: Clean Java Language Server Workspace`.
	- Puis recharger la fenetre VS Code.
- Si les vues Java/Spring ne s'affichent pas au premier chargement:
	- Ouvrir un fichier applicatif Java (ex: classe `*Application.java`) pour declencher l'indexation.
	- Recharger la fenetre VS Code si necessaire.
- Si Maven n'apparait pas dans la vue:
	- Verifier `mvn -v` en terminal.
	- Recharger la fenetre VS Code.
- Si Spring Dashboard vide:
	- Verifier presence de `@SpringBootApplication` dans la classe principale.

### 8) Criteres de prise en main validee
- Projet visible dans Java Projects.
- Application visible dans Spring Boot Dashboard.
- Tests executables depuis Test Explorer.
- Build Maven executables depuis vue Maven et terminal.
- Debug session fonctionnelle avec breakpoint.

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

## Journal d'installation et verifications
- Regle: chaque changement d'outillage doit etre trace ici au fil de l'eau.
- Format recommande:
	- Date
	- Cle Jira
	- Outil installe/mis a jour
	- Commandes executees
	- Resultat observe
	- Action suivante

Entree courante:
- Date: 2026-04-10
- Cle Jira: A.3.3 (setup outillage local pour suite B.1.1)
- Outils verifies: VS Code Java/Spring extensions, JDK 21, Maven 3.9+
- Resultat: environnement backend VS Code operationnel (Java 21 et Maven detectes)

Entree courante (validation finale apres reinstallation):
- Date: 2026-04-10
- Cle Jira: A.3.3 (stabilisation outillage backend local)
- Outils verifies: JDK 21, JAVA_HOME, Maven (Scoop), resolution des bins
- Commandes executees:
	- `java -version`
	- `javac -version`
	- `$env:JAVA_HOME`
	- `where.exe java`
	- `where.exe mvn`
- Resultat observe:
	- Java runtime: 21.0.1
	- Java compiler: 21.0.1
	- JAVA_HOME: defini sur JDK 21
	- java resolu avec JDK 21 en premier dans le PATH
	- mvn resolu via Scoop (`...\\scoop\\apps\\maven\\current\\bin\\mvn(.cmd)`)

Entree courante (validation B.1.1 - outillage + build):
- Date: 2026-04-10
- Cle Jira: B.1.1
- Contexte: projet Spring Boot genere, vues Java chargees apres ouverture d'un fichier applicatif.
- Commandes executees:
	- `mvn test`
	- `mvn -DskipTests clean package`
- Resultat observe:
	- `TEST_OK` (profil `test` actif)
	- `PACKAGE_OK`
- Note technique:
	- Le test de contexte necessite une datasource de test (H2) pour eviter une dependance PostgreSQL locale.
- Action suivante:
	- Poursuivre le cadrage B.1.1/B.1.2 (config profils et base de donnees dev/test).

