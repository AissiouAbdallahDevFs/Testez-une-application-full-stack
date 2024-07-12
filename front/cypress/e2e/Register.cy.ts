/// <reference types="cypress" />

describe('RegisterComponent', () => {

    beforeEach(() => {
        // Visiter la page de registrement avant chaque test
        cy.visit('/register');
    });

    it('should display registration form', () => {
        // Vérifier que le formulaire de registrement s'affiche correctement
        cy.get('mat-card-title').should('contain', 'Register');
        cy.get('input[formControlName=firstName]').should('be.visible');
        cy.get('input[formControlName=lastName]').should('be.visible');
        cy.get('input[formControlName=email]').should('be.visible');
        cy.get('input[formControlName=password]').should('be.visible');
        cy.get('button[type=submit]').should('be.disabled');
    });

    it('should enable the submit button when the form is valid', () => {
        // Remplir le formulaire avec des données valides
        cy.get('input[formControlName=firstName]').type('John');
        cy.get('input[formControlName=lastName]').type('Doe');
        cy.get('input[formControlName=email]').type('john.doe@example.com');
        cy.get('input[formControlName=password]').type('validpassword');
        
        // Vérifier que le bouton de soumission est activé
        cy.get('button[type=submit]').should('not.be.disabled');
    });

   it('should display error messages when the form is invalid', () => {
        // Remplir le formulaire avec des données invalides
        cy.get('input[formControlName=firstName]').type('John');
        cy.get('input[formControlName=lastName]').type('Doe');
        cy.get('input[formControlName=email]').type('invalidemail');
        cy.get('input[formControlName=password]').type('invalid');
        
        // Vérifier que le bouton de soumission est désactivé
        cy.get('button[type=submit]').should('be.disabled');
        
    });

    it('should register a new user', () => {
        // Intercepter la requête de registrement et fournir une réponse simulée
        cy.intercept('POST', '/api/auth/register', {
            body: {
                id: 1,
                username: 'effé',
                firstName: 'John',
                lastName: 'Doe',
                admin: false
            },
        }).as('registerRequest');
        
        // Remplir le formulaire avec des données valides
        cy.get('input[formControlName=firstName]').type('John');
        cy.get('input[formControlName=lastName]').type('Doe');
        cy.get('input[formControlName=email]').type('test@gmail.com')
        cy.get('input[formControlName=password]').type('validpassword');


        // Cliquer sur le bouton de soumission
        cy.get('button[type=submit]').click();

        // Attendre que la requête de registrement soit terminée
        cy.wait('@registerRequest').its('response.statusCode').should('eq', 200);


        // Vérifier que l'utilisateur est redirigé vers la page de connexion
        cy.url().should('include', '/login');
    });

    it('should display an error message if registration fails', () => {
        // Intercepter la requête de registrement et fournir une réponse d'erreur
        cy.intercept('POST', '/api/auth/register', {
            statusCode: 400,
            body: {
                message: 'Invalid data'
            },
        }).as('registerRequest');
        
        // Remplir le formulaire avec des données valides
        cy.get('input[formControlName=firstName]').type('John');
        cy.get('input[formControlName=lastName]').type('Doe');
        cy.get('input[formControlName=email]').type('triste@gmail.com')
        cy.get('input[formControlName=password]').type('validpassword');

        // Cliquer sur le bouton de soumission
        cy.get('button[type=submit]').click();

        // Attendre que la requête de registrement soit terminée
        cy.wait('@registerRequest').its('response.statusCode').should('eq', 400);

        // Vérifier que le message d'erreur s'affiche
        cy.get('.error').should('be.visible');
    }
    );

    it('should redirect to login page when clicking on the login link', () => {
        // Cliquer sur le lien de connexion
        cy.contains('Login').click();

        // Vérifier que l'utilisateur est redirigé vers la page de connexion
        cy.url().should('include', '/login');
    });
});
    


