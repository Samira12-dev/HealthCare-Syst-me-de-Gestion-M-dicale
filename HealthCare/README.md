#HealthCare+ : Système de Gestion Médicale

## Description
Le Système de Gestion Médicale est une application web développée avec Spring Boot.

Elle permet de :

# Gestion des Patients
# Gestion des Médecins
# Gestion des Rendez-vous
# Gestion Dossier Médical



## Fonctionnalités
- Créer, modifier, supprimer ,lister et consulter les patients
- Créer, modifier, supprimer et consulter les medecins
- Gérer les dossiers medical pour patient (créé dossier medical, ajouter diagnostic,ajouter observation avec date de création)
- Gérer les rendez-vous entre patients et médecins
- API REST documentée avec Swagger
- creation database avec migration

## Technologies Utilisées
- Java 17 / 21
- Spring Boot
- Spring Data JPA / Hibernate / Flyway
- Maven
- SQL & Jointures
- Derived Queries / @Query (SQL et JPQL)
- Architecture MVC
- REST API
- DTO & Mapper (mapstruct)
- JUnit
- Docker & Dockerfile
- Swagger
- Git & Gitignore

## Structure du projet
src/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
└── mapper/


##  les trois diagrammes UM

# Diagramme de Classes
![img_2.png](img_2.png)

# Diagramme de Cas d'Utilisation
![img_3.png](img_3.png)

# Diagramme de Séquence

- exapmle pour ajouter 

![img_4.png](img_4.png)


- example pour lister medecins

![img_5.png](img_5.png)

- example pour supprimer 

![img_6.png](img_6.png)

- exemple recherche rendez_vous par patient

![img_7.png](img_7.png)








