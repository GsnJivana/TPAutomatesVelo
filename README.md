L3 CMPL - TP Automates - Locations vélos
========================================

# VSCode

Dépendances et extensions VSCode :
- Extension Pack for Java  v0.30.5 

Pour charger correctement le projet dans VSCode, il faut ouvrir le **répertoire** racine `TPAutomatesVelo`, et non un répertoire parent.
Cela permet à VSCode d'identifier correctement les fichiers de configurations présents dans le répertoire `.vscode`.

Pour démarrer l'extension Java de VSCode, ouvrez simplement un fichier source `.java`, et patientez.

# Compilation

La compilation peut être réalisée entièrement par VSCode, semi-automatiquement.
Pour cela, l'extension Java de VSCode doit être déjà en cours d'exécution.
Dans la vue "Java Project", cliquer sur l'icône des outils "Rebuild All".

Une compilation manuelle plus standard peut également être réalisée.
Mais n'oubliez pas de produire les fichiers `.class` dans le répertoire `bin`.

Voici un exemple de commande, à exécuter depuis la racine du projet :

```
javac -cp lib/libIO.jar:lib/libGUI.jar -d bin/ src/*/*.java
```

# Exécution

L'application principale est `main.Velo`. Pour l'exécuter, deux options :
- directement dans VSCode, avec un clic droit sur le fichier `src/main/Velo.java`, puis en choisissant "Run Java"
- en ligne de commande standard, avec la commande suivante, depuis la racine du projet

  ```
  java -cp lib/libIO.jar:lib/libGUI.jar:bin/ main.Velo chemin/vers/nomFichierTest.txt
  ```


