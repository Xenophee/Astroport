
<h1 align="center">Astroport</h1>
<h2 align="center">Trouvez votre place dans l'univers</h2>

<img src="/Astroport.png" alt="Logo de l'application">


## Description

Système de gestion d'ammarrage pour vaisseaux spatiaux. C'est comme pour un parking, mais en mieux parce qu'on prévoit l'horizon 2075 ! Si, si !

## Fonctionnalités

- Prise en charge de la langue anglaise et française
- Gestion des quais d'amarrage
- Tarification en fonction de la durée et du type de vaisseau à quai
- Les trente premières minutes sont gratuites
- Application d'une réduction de 10% pour les clients réguliers au bout de 5 amarrages

## Prérequis

- **Java** : Version 21 ou supérieure
- **Gradle** : Version 8.0 ou supérieure (inclus dans le projet grâce au wrapper gradlew)
- **Base de données** : MySQL

## Installation

1. Clonez le dépôt :
   ```sh
   git clone https://github.com/Xenophee/astroport.git
    ```
   
2. Créez la base de données grâce au script SQL fourni `Data.sql` dans le dossier `resources` et configurez les paramètres de connexion dans le fichier `database.properties`.

3. Compilez le projet :
   ```sh
   ./gradlew build
   ```
   
4. Exécutez le projet :
   ```sh
   ./gradlew run
   ```
