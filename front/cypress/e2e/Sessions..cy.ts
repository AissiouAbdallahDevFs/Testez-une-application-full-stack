/// <reference types="cypress" />


describe('sessions spec', () => {

   
    it('User is redirected to login page if not authenticated', () => {
        // Visiter la page de profil
        cy.visit('/sessions');
  
        // Vérifier que l'URL contient '/login' après la redirection
        cy.url().should('include', '/login');
    });
  
    it('User is able to access Session page if authenticated', () => {
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
    });

    
  });
  