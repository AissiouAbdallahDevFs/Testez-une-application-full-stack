/// <reference types="cypress" />

/// <reference types="cypress" />

describe('Session with admin credentials', () => {

    beforeEach(() => {
        // Simuler la connexion en tant qu'administrateur
        cy.visit('/login');

        cy.intercept('POST', '/api/auth/login', {
            body: {
                id: 1,
                username: 'yoga@studio.com',
                firstName: 'Admin',
                lastName: 'User',
                admin: true
            },
        }).as('loginRequest');

        cy.get('input[formControlName=email]').type("yoga@studio.com");
        cy.get('input[formControlName=password]').type("test!1234");
        cy.get('button[type=submit]').click();
    });

    it('Create and display new session', () => {

        cy.intercept('POST', '/api/session', {
            body: {
                id: 1,
                name: 'totoekfe',
                description: 'the best session ever',
                date: '2024-07-26T00:00:00.000Z',
                teacher_id: 1
            }
        }).as('createSession');

        cy.intercept('GET', '/api/teacher', {
            body: [{
                id: 1,
                firstName: 'Margot',
                lastName: 'DELAHAYE'
            }]
        }).as('getTeachers');

        cy.intercept('GET', '/api/sessions', {
            body: [{
                id: 1,
                name: 'totoekfe',
                description: 'the best session ever',
                date: '2024-07-26T00:00:00.000Z',
                teacher: {
                    id: 1,
                    firstName: 'Margot',
                    lastName: 'DELAHAYE'
                }
            }]
        }).as('getSessions');

        cy.get('button[routerLink=create]').click();
        cy.get('input[formControlName=name]').type("totoekfe");
        cy.get('input[formControlName=date]').type("2024-07-26");
        cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
        cy.get('textarea[formControlName=description]').type("the best session ever");
        cy.get('button[type=submit]').click();

   

        // Vérifier que l'URL inclut '/sessions'
        cy.url().should('include', '/sessions');


    });
});
