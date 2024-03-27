# SafetyNet Alerts

## Description
SafetyNet Alerts est une application qui aide à sauver des vies.
Cette API REST met à disposition des services d'urgence  des informations leur permettant de mieux préparer et appréhender toutes les situations.
Par exemples : En cas d'ouragan, prévenir les personnes concernées via sms, En cas d'incendie, connaitre les personnes résidentes.

Cette application a été réalisée dans le cadre d'une formation OpenClassRooms.

##  Installation

- pré-requis : 
    - JAVA : 21 (ou 17)
    - Maven : 3.9.5
 

- Cloner le repo dans le répertoire de votre choix.
 

- Intégrer le Fichier JSON support des données: 
  - Le fichier est disponible ici: https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json. Une copie locale en date du 13/11/2023 est également disponible dans `/resources`.    
  - Déposer le fichier dans le répertoire de votre choix.
  - Indiquer l'emplacement du fichier JSON dans le fichier `application.properties` (remplacer les `XXXXXX`). 
 

- Adapter les autres variables d'environnement selon vos besoins :
  - le port (8080 par défaut)
  - les actuators mis à disposition
  - le niveau de log dans le fichier applicationproperties
 

- Packager l'application :
  - depuis votre IDE
  - depuis le terminal dans le répertoire du projet :`mvn package`
   
  
- Lancer l'application :
  - depuis votre IDE
  - depuis le terminal dans répertoire `/target` :`java -jar alerts-0.0.1-SNAPSHOT.jar`
  - une log INFO confirme lebon lancement de l'application : `Loading jsonDatabase complete. Ready to save people !`

##  Tests
- Le fichier JSON de données utilisé pour les test est intégré : `test/resources/dataTest.json`.

## Rapports
- jacoco : le rapport est généré ici : `/target/site/jacoco/index.html`. (après `mvn test` ou phase supérieure)
- surefire : le rapport est généré ici `/target/site/surefire-report.html` (après `mvn site`)

## Collection postman
Un ensemble de tests sur postman est disponible dans `/postman`

## Endspoints

- http://localhost:8080/person

  Cet endpoint permet d’effectuer les actions suivantes via Post/Put/Delete avec HTTP :
  - ajouter une nouvelle personne ;
  -  mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
  famille ne changent pas, mais que les autres champs peuvent être modifiés) ;
  - supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur
  unique).
   

- http://localhost:8080/firestation

  Cet endpoint permet d’effectuer les actions suivantes via Post/Put/Delete avec HTTP :
  - ajout d'un mapping caserne/adresse ;
  - mettre à jour le numéro de la caserne de pompiers d'une adresse ;
  - supprimer le mapping d'une caserne ou d'une adresse. 
 

- http://localhost:8080/medicalRecord

  Cet endpoint permet d’effectuer les actions suivantes via Post/Put/Delete HTTP :
  - ajouter un dossier médical ;
  - mettre à jour un dossier médical existant (comme évoqué précédemment, supposer que le
  prénom et le nom de famille ne changent pas) ;
  - supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme
  identificateur unique


- http://localhost:8080/firestation?stationNumber=<station_number>

  Cette url retourne une liste des personnes couvertes par la caserne de pompiers correspondante. La liste
inclue les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone, un décompte du nombre d'adultes et du nombre d'enfants) dans la zone desservie.

  
- http://localhost:8080/childAlert?address=<address>

  Cette url retourne une liste d'enfants habitant à cette adresse.
La liste comprend le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. 


- http://localhost:8080/phoneAlert?firestation=<firestation_number>

  Cette url retourne une liste des numéros de téléphone des résidents desservis par la caserne de
pompiers.
 

- http://localhost:8080/fire?address=<address>

  Cette url retourne la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
de pompiers la desservant. La liste inclue le nom, le numéro de téléphone, l'âge et les antécédents
médicaux (médicaments, posologie et allergies) de chaque personne.
 

- http://localhost:8080/flood/stations?stations=<a_list_of_station_numbers>

  Cette url retourne une liste de tous les foyers desservis par la caserne. Cette liste regroupe les
personnes par adresse. Elle inclue aussi le nom, le numéro de téléphone et l'âge des habitants, leurs antécédents médicaux (médicaments, posologie et allergies).
 

- http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>

  Cette url retourne le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles apparaissent toutes.
 

- http://localhost:8080/communityEmail?city=<city>

  Cette url retourne les adresses mail de tous les habitants de la ville.

