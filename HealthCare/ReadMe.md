# HealthCare+ : Système de Gestion Médicale

## 1. Nom du projet

**HealthCare+ : Système de Gestion Médicale**

---

# 2. Présentation du projet

HealthCare+ est une application backend développée avec Spring Boot permettant de gérer les activités principales d'une clinique.

L'application permet de gérer les patients, les médecins, les rendez-vous et les dossiers médicaux à travers une API REST sécurisée.

Elle s'adresse principalement aux cliniques et aux professionnels de santé qui souhaitent centraliser leurs informations médicales et améliorer le suivi des patients.

Son objectif principal est de digitaliser la gestion médicale, sécuriser l'accès aux données et faciliter l'organisation des opérations quotidiennes.

---

# 3. Problématique

La gestion manuelle des informations médicales peut provoquer des difficultés dans le suivi des patients, la gestion des rendez-vous et la conservation des dossiers médicaux.

La solution proposée avec HealthCare+ permet de centraliser les données médicales dans une application sécurisée, d'automatiser les opérations de gestion et de contrôler l'accès aux informations grâce à l'authentification JWT.

---

# 4. Fonctionnalités principales

## Gestion des patients

- Ajouter un patient.
- Modifier les informations d'un patient.
- Supprimer un patient.
- Consulter la liste des patients.
- Consulter les détails d'un patient.

## Gestion des médecins

- Ajouter un médecin.
- Modifier les informations d'un médecin.
- Supprimer un médecin.
- Consulter la liste des médecins.

## Gestion des rendez-vous

- Créer un rendez-vous.
- Modifier un rendez-vous.
- Annuler un rendez-vous.
- Consulter la liste des rendez-vous.
- Rechercher un rendez-vous par patient.
- Rechercher un rendez-vous par médecin.

## Gestion des dossiers médicaux

- Créer un dossier médical.
- Ajouter un diagnostic.
- Ajouter des observations.
- Consulter un dossier médical.

---

# 5. Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 21 | Langage principal utilisé pour le développement backend |
| Spring Boot | Création de l'application backend et des API REST |
| Spring Data JPA | Gestion de la persistance des données |
| Hibernate | Mapping entre les objets Java et la base de données |
| MySQL | Stockage des données |
| Flyway | Gestion des migrations de base de données |
| Maven | Gestion des dépendances et du build du projet |
| Spring Security | Sécurisation de l'application |
| JWT | Authentification et protection des endpoints |
| MapStruct | Conversion entre Entity et DTO |
| JUnit | Réalisation des tests unitaires |
| Docker | Conteneurisation de l'application |
| Redis | Gestion du cache pour améliorer les performances |
| Swagger | Documentation et test des API REST |

---

# 6. Architecture du projet

L'application suit une architecture en couches :

```
src/

├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── mapper/
├── config/
├── security/
├── filter/
└── exception/
```

## Rôle des couches

- **Controller** : Gestion des requêtes HTTP et exposition des endpoints REST.
- **Service** : Contient la logique métier de l'application.
- **Repository** : Communication avec la base de données.
- **Entity** : Représentation des tables de la base de données.
- **DTO** : Transport des données entre les différentes couches.
- **Mapper** : Conversion entre Entity et DTO.
- **Security** : Configuration de l'authentification et de la sécurité.
- **Exception** : Gestion globale des erreurs.

---

# 7. Diagrammes UML

## Diagramme de classes

![Diagramme de classes](images/img_2.png)

## Diagramme de cas d'utilisation

![Diagramme de cas d'utilisation](images/UseCaseDiagram333.jpg)

## Diagrammes de séquence

### Ajouter un élément

![Ajouter](images/img_4.png)

### Lister les médecins

![Lister médecins](images/img_5.png)

### Supprimer un élément

![Supprimer](images/img_6.png)

### Recherche rendez-vous par patient

![Recherche rendez-vous](images/img_7.png)

---

# 8. Sécurisation avec Spring Security & JWT

## Description

Dans cette partie, l'application HealthCare+ a été sécurisée avec Spring Security et JWT afin de protéger les endpoints et permettre uniquement aux utilisateurs authentifiés d'accéder aux fonctionnalités médicales.

---

## Fonctionnalités ajoutées

### Authentification utilisateur

- Inscription utilisateur.
- Connexion utilisateur.
- Génération du token JWT.
- Validation du token JWT.
- Gestion de l'expiration du token.
- Chiffrement des mots de passe avec BCrypt.

### Sécurisation API

Les endpoints suivants restent accessibles publiquement :

```
POST /auth/register
POST /auth/login
```

Les autres ressources nécessitent une authentification :

- Patients.
- Médecins.
- Rendez-vous.
- Dossiers médicaux.

---

## Flux d'authentification JWT

1. L'utilisateur crée un compte.

```
POST /auth/register
```

2. Le mot de passe est chiffré avec BCrypt.

3. L'utilisateur se connecte.

```
POST /auth/login
```

4. Spring Security vérifie les informations.

5. Un token JWT est généré.

6. Le token est envoyé dans chaque requête :

```
Authorization: Bearer token
```

7. Le JWT Filter valide le token.

8. L'utilisateur obtient l'accès aux ressources protégées.

---

# 9. Pagination, rôles et Docker

## Pagination

Ajout de la pagination pour :

- Patients.
- Médecins.
- Rendez-vous.
- Dossiers médicaux.

## Recherche paginée

Ajout des recherches avec pagination :

- Recherche patient par nom.
- Recherche médecin par spécialité.
- Recherche rendez-vous par statut.

---

## Gestion des rôles

L'application utilise trois rôles :

### ADMIN

Peut gérer :

- Patients.
- Médecins.
- Rendez-vous.
- Dossiers médicaux.
- Utilisateurs.

### MEDECIN

Peut :

- Consulter ses rendez-vous.
- Consulter les dossiers médicaux.
- Ajouter des diagnostics.

### PATIENT

Peut :

- Consulter son profil.
- Consulter ses rendez-vous.
- Consulter son dossier médical.

---

## Docker

L'application peut être exécutée avec Docker grâce à :

- Dockerfile.
- Docker Compose.

---

# 10. Optimisation et déploiement

## Cache Redis

Redis est utilisé afin d'améliorer les performances des données fréquemment consultées :

- Liste des patients.
- Liste des médecins.
- Liste des rendez-vous.
- Dossier médical d'un patient.

Le cache est invalidé après :

- Création.
- Modification.
- Suppression.

---

## Téléchargement des fichiers

Ajout d'une fonctionnalité permettant de générer des fichiers depuis l'API :

- Export d'un dossier médical.
- Génération de rapports.

---

## CI/CD GitHub Actions

Mise en place d'un pipeline permettant :

- Compilation automatique du projet.
- Exécution des tests.
- Vérification du build.

Le workflow est exécuté lors des :

- Push.
- Pull Request.

---

# 11. Installation et lancement

## Prérequis

- Java 21.
- Maven.
- MySQL.
- Docker.
- Git.

---

## Cloner le projet

```bash
git clone lien_du_repository
```

---

## Configuration MySQL

Créer une base de données :

```sql
CREATE DATABASE healthcare_db;
```

Configurer le fichier :

```
application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_db
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Lancer l'application

```bash
mvn spring-boot:run
```

L'API sera disponible :

```
http://localhost:8080
```

Swagger :

```
http://localhost:8080/swagger-ui/index.html
```

---

# 12. Contribution personnelle

Ma contribution principale a porté sur le développement backend de l'application HealthCare+.

J'ai réalisé la création des API REST pour la gestion des patients, médecins, rendez-vous et dossiers médicaux.

J'ai également travaillé sur la sécurisation avec Spring Security et JWT, la gestion de la base de données avec JPA/Hibernate, ainsi que l'amélioration des performances avec Redis et la conteneurisation avec Docker.

---

# 13. Difficultés rencontrées

## Configuration JWT

J'ai rencontré des difficultés lors de la mise en place de Spring Security et du filtre JWT.

J'ai effectué des recherches sur SecurityFilterChain, UserDetailsService et la gestion du contexte de sécurité.

La solution a été d'organiser correctement la configuration de sécurité et le processus d'authentification.


## Gestion des relations JPA

J'ai rencontré des difficultés concernant les relations entre les entités et le chargement des données.

J'ai utilisé les annotations JPA et vérifié la structure des relations afin de résoudre ces problèmes.


## Gestion des migrations

J'ai rencontré des problèmes liés aux migrations de base de données.

J'ai utilisé Flyway pour gérer les versions du schéma et assurer la cohérence de la base de données.

---

# 14. Améliorations possibles

Dans une prochaine version, je pourrais :

- Ajouter plus de tests automatisés.
- Ajouter un système de notifications pour les rendez-vous.
- Améliorer la documentation API.
- Ajouter un système de statistiques médicales.

Ces améliorations permettraient de rendre l'application plus complète et plus proche d'un environnement professionnel.