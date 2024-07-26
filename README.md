# yoga-app- Testing

Ce repository contient les artefacts liés au testing de l'application yoga-app avant son lancement.

## Détails du Testing

Le testing a été réalisé conformément au plan fourni par l'équipe. Il couvre les aspects front-end, back-end et end-to-end. La couverture de code atteint 85 %, avec 35 % de tests d'intégration.

## Structure du Projet

```
├── back/
│   ├── [Dossiers du code source back-end]
├── front/
│   ├── [Dossiers du code source front-end]
├── ressources/
│   ├── postman/
│   │   └── yoga.postman_collection.json
│   └── sql/
│       └── script.sql
```

## Installation

### Back-end (Java avec Spring)

1. Assurez-vous d'avoir Maven installé.
2. Accédez au répertoire `backend`.
3. Exécutez la commande suivante :

```bash
mvn install
```

### Front-end (Angular)

1. Assurez-vous d'avoir Node.js et npm installés.
2. Accédez au répertoire `frontend`.
3. Exécutez la commande suivante :

```bash
npm install
```

## Lancement de l'Application

### Back-end (Java avec Spring)

1. Accédez au répertoire `backend`.
2. Exécutez la commande suivante :

```bash
mvn spring-boot:run
```

L'API sera accessible à l'adresse : `http://localhost:8080`

### Front-end (Angular)

1. Accédez au répertoire `frontend`.
2. Exécutez la commande suivante :

```bash
ng serve
```

L'application Angular sera accessible à l'adresse : `http://localhost:4200`

## Démarrage du Projet

Git clone:

```bash
git clone https://github.com/AissiouAbdallahDevFs/Testez-une-application-full-stack.git
```

Allez dans le dossier:

```bash
cd yoga
```

Installez les dépendances:

```bash
npm install
```

Lancez le Front-end:

```bash
npm run start
```
Lancez le back-end:

```bash
mvn spring-boot:run
```



### MySQL

Le script SQL pour créer le schéma est disponible dans `ressources/sql/script.sql`.

Par défaut, le compte admin est :
- login: yoga@studio.com
- password: test!1234

## Tests

### E2E

Lancer les tests e2e :

```bash
npm run e2e
```

Générer le rapport de couverture (vous devez lancer les tests e2e avant) :

```bash
npm run e2e:coverage
```

Le rapport est disponible ici :

```bash
front/coverage/lcov-report/index.html
```
```bash
back\target\site\jacoco
```
```bash
back\target\site\jacoco
```

### Tests 

Lancer les tests front :

```bash
npm run test
```
Lancer les tests back :

```bash
mvn test
```



**Auteur:** [AISSIOU]