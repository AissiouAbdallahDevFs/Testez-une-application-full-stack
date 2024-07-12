/// <reference types="cypress" />

describe('Logout', () => {
    it('should logout successfully', () => {
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

        // Attendre que la requête de login soit complétée
        cy.wait('@loginRequest');

        // Vérifier que l'utilisateur est connecté (ajoutez une vérification selon votre application)
        // Par exemple, vérifier la présence d'un élément spécifique à la page après connexion
        cy.contains('Logout').should('be.visible');

        // Cliquer sur le bouton de déconnexion
        cy.contains('Logout').click();

        // Intercepter la requête de déconnexion si elle existe et attendre sa réponse
        cy.intercept('POST', '/api/auth/logout', {
            statusCode: 200
        }).as('logoutRequest');

        cy.visit('/login');

    });

    


});
