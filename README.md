
# yoga-app- Testing

Ce repository contient les artefacts liés au testing de l'application yoga-app avant son lancement.

## Détails du Testing

Le testing a été réalisé conformément au plan fourni par l'equipe . Il couvre les aspects front-end, back-end et end-to-end. La couverture de code atteint 85 %, avec 35 % de tests d'intégration.

## Structure du Projet

```
├── frontend/
│   ├── [Dossiers du code source front-end]
├── backend/
│   ├── [Dossiers du code source back-end]
├── tests/
│   ├── [Dossiers des tests]
├── documentation/
│   ├── testing_plan.pdf
│   └── coverage_report.pdf
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



**Auteur:** [AISSIOU]
```


