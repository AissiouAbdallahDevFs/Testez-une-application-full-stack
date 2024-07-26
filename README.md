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
## Démarrage du Projet

Git clone:

```bash
git clone https://github.com/AissiouAbdallahDevFs/Testez-une-application-full-stack.git
```

Allez dans le dossier:

```bash
cd Testez-une-application-full-stack
```
```bash
cd front
```
Installez les dépendances:

```bash
npm install
```
Lancez le Front-end:

```bash
ng serve
```

L'application Angular sera accessible à l'adresse : `http://localhost:4200`

```bash
cd béack
```

```bash
mvn install
```
Lancez le back-end:

```bash
mvn spring-boot:run
```

L'API sera accessible à l'adresse : `http://localhost:8080`
Installez les dépendances:



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