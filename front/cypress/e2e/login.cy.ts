/// <reference types="cypress" />

describe('Login spec', () => {
  beforeEach(() => {
      // Intercepter la requête de session et fournir une réponse vide
      cy.intercept('GET', '/api/session', []).as('session');
  });

  it('Login successful', () => {
      // Visiter la page de login
      cy.visit('/login');

      // Intercepter la requête de login et fournir une réponse simulée
      cy.intercept('POST', '/api/auth/login', {
          body: {
              id: 1,
              username: 'userName',
              firstName: 'firstName',
              lastName: 'lastName',
              admin: true
          },
      }).as('loginRequest');

      // Entrer les informations de connexion
      cy.get('input[formControlName=email]').type("yoga@studio.com");
      cy.get('input[formControlName=password]').type("test!1234");
      cy.get('button[type=submit]').click();

      // Vérifier que la requête de login est terminée avec succès
      cy.wait('@loginRequest').its('response.statusCode').should('eq', 200);

      // Vérifier que l'URL contient '/sessions' après la connexion
      cy.url().should('include', '/sessions');
  });

  it('Login unsuccessful', () => {
      // Visiter la page de login
      cy.visit('/login');

      // Intercepter la requête de login et fournir une réponse d'erreur
      cy.intercept('POST', '/api/auth/login', {
          statusCode: 401,
          body: {
              message: 'Invalid credentials'
          },
      }).as('loginRequest');

      // Entrer les informations de connexion
      cy.get('input[formControlName=email]').type("renedecarts@gmail.com");
      cy.get('input[formControlName=password]').type("test!1234");
      cy.get('button[type=submit]').click();

      // Vérifier que la requête de login échoue
      cy.wait('@loginRequest').its('response.statusCode').should('eq', 401);

      // Vérifier que l'URL contient '/login' après l'échec de la connexion
      cy.url().should('include', '/login');

  });

  it('should disable submit button if password is empty', () => {
      // Visiter la page de login
      cy.visit('/login');

      // Entrer un email valide sans mot de passe
      cy.get('input[formControlName=email]').type("yoga@studio.com");
      cy.get('input[formControlName=password]').clear();

        // Vérifier que le champ password est invalide
        cy.get('input[formControlName=password]').should('have.class', 'ng-invalid');
        
  });

  it('should disable submit button if email is empty', () => {
      // Visiter la page de login
      cy.visit('/login');

      // Entrer un mot de passe valide sans email
        cy.get('input[formControlName=email]').clear();
        cy.get('input[formControlName=password]').type("test!1234");

        // Vérifier que le champ email est invalide
        cy.get('input[formControlName=email]').should('have.class', 'ng-invalid');

  });

    it('should disable submit button if email and password are empty', () => {
        // Visiter la page de login
        cy.visit('/login');
    
        // Vérifier que le bouton de soumission est désactivé
        cy.get('button[type=submit]').should('be.disabled');
    });
});
