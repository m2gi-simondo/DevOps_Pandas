# Pandas
Implémentation de DataFrame de la bibliothèque pandas en java
[![Coverage](.github/badges/jacoco.svg)](https://github.com/Dorian-Gray73/DevOps_Pandas/actions/workflows/maven.yml)
[![Branches](.github/badges/branches.svg)](https://github.com/Dorian-Gray73/DevOps_Pandas/actions/workflows/maven.yml)

## Fonctionnalités
* DataFrame(ArrayList<String> label, ArrayList<ArrayList<?>> tableau) : Génération d'un DataFrame à partir d'une liste de labels (String) et une table de données
* labelSelection : Création d'un DataFrame à partir d'une colonne
* ligneIndexSelection : Création d'un DataFrame à partir d'une sélection de lignes
* nbLigne : retourne le nombre de lignes d'un DataFrame
* nbColonne : retourne le nombre de colonnes d'un DataFrame
* getColumn : retourne une colonne d'un DataFrame à l'aide d'un DataFrame
* head () : retourne les 10 premières lignes du DataFrame
* head(int n) : retourne les n premières lignes du DataFrame

Pour des valeurs numériques nous proposons les services ci-dessous :
* min : retourne le minimum d'une colonne
* max : retourne le maximum d'une colonne
* moyenne : retourne la moyenne d'une colonne
* mediane : retourne la médiane d'une colonne
* premier quartile : retourne le premier quartile d'une colonne 
* troisième quartile : retourne le troisième quartile d'une colonne 
* distance distanceInterQuartile : retourne la distance entre le premier quartile et le troisième quartile

## Outils 
1. Maven : Outil de gestion et d'automatisation de production des projets logiciels Java
2. Git et Github : Outils de gestion de version.
3. Github actions : Outil d'intégration continue proposer par github. Nous avons choisi github car nous l'utilisons depuis de nombreuses années.
4. Jacoco: Outil d'évalutation de couverture de code
5. Docker: Outil permettant de créer une image faisant une démonstration de notre bibliothèque.

## Workflow
Nous travaillons avec le workflow Feature Branch. Pour ajouter une nouvelle fonctionnalité (ou une même famille de fonctionnalités), nous créons une nouvelle branche dédiée.
Une fois la fonctionnalité développée nous fusionnons le master sur cette branche pour résoudre les conflits. Après la résolution des conflits, une pull request est créer pour effectuer une revue de code avant de fusionner la branche dans master.
Pour valider une pull request, le code doit être validé par au moins un autre développeur n'ayant pas participé au développement.

## Intégration continue
Le script d'intégration continue compile notamment le projet java à l'aide de maven.
Le pom.xml est configuré de manière à exploiter jacoco pour exécuter les tests et avoir des statistiques sur la couverture du code.
Si la couverture du code est insuffisante, le push ou la pull request est refusée.
Sinon, les badges de jacoco sont committés et poussés sur le dépôt.

## Docker
Nous avons commencé un travail avec docker.
Le dockerfile actuel est capable compiler le projet avec maven et d'exécuter un programme java affichant "Hello World!".
La suite serait de créer un programme java capable de faire une démonstration des fonctionnalités de notre bibliothèque DataFrame.
De plus, il serait utile de générer automatiquement l'image docker et de la pousser vers DockerHub dans l'intégration continue.
## Documentation
Il est possible de générer la javadoc avec la commande : mvn javadoc:javadoc
Elle est créée dans pandas/target/site/apidocs.