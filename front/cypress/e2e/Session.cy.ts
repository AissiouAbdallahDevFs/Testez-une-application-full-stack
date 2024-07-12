/// <reference types="cypress" />
describe('Session Management', () => {
        beforeEach(() => {
            cy.intercept('POST', '/api/auth/login', {
                body: {
                    id: 1,
                    username: 'yoga@studio.com',
                    firstName: 'firstName',
                    lastName: 'lastName',
                    admin: true
                },
            }).as('login');
        
            cy.intercept('GET', '/api/session', {
                body: [
                    {
                        "id": 1,
                        "name": "Meditation Techniques",
                        "date": "2024-07-30T10:00:00.000Z",
                        "teacher_id": 2,
                        "description": "A session on various meditation techniques",
                        "users": [],
                        "createdAt": "2024-07-01T10:00:00.000Z",
                        "updatedAt": "2024-07-10T10:00:00.000Z"
                    }
                ]
            }).as('session');

            cy.intercept('DELETE', '/api/session/*', {
                statusCode: 200,
                body: {}
            }).as('deleteSession');
        });

        it('create session', () => {
            cy.visit('/login');
        
            cy.get('input[formControlName=email]').type("yoga@studio.com");
            cy.get('input[formControlName=password]').type("yourPassword");
            cy.get('button[type=submit]').click();

            cy.wait('@login');
            cy.wait('@session');

            cy.intercept('GET', '/api/teacher', {
                body: [{
                    id: 1,
                    firstName: 'Margot',
                    lastName: 'DELAHAYE'
                }]
            }).as('getTeachers');

            cy.intercept('POST', '/api/session', {
                body: {
                    id: 1,
                    name: 'Meditation Techniques',
                    description: 'A session on various meditation techniques',
                    date: '2024-07-30T10:00:00.000Z',
                    teacher_id: 2,
                    users: []
                }
            }).as('createSession');

            cy.contains('button', 'Create').click();

            cy.get('input[formControlName=name]').type("totoekfe");
            cy.get('input[formControlName=date]').type("2024-07-26");
            cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
            cy.get('textarea[formControlName=description]').type("the best session ever");
            cy.get('button[type=submit]').click();
    
       
    
            // Vérifier que l'URL inclut '/sessions'
            cy.url().should('include', '/sessions');
        });


        it('delete session', () => {
            cy.visit('/login');
            
            cy.get('input[formControlName=email]').type("yoga@studio.com");
            cy.get('input[formControlName=password]').type("yourPassword");
            cy.get('button[type=submit]').click();
            
            cy.wait('@login');
            
            cy.wait('@session');
            
            // Intercept GET session/1 to simulate details retrieval
            cy.intercept('GET', '/api/session/1', {
                body: {
                    id: 1,
                    name: "Meditation Techniques",
                    description: "A session on various meditation techniques",
                    createdAt: "2024-07-01T10:00:00.000Z",
                    teacher_id: 2,
                    users: []
                }
            }).as('getSessionDetail');

            // Intercept GET teacher/2 for session details
            cy.intercept('GET', '/api/teacher/2', {
                body: {
                    id: 2,
                    firstName: 'Teacher',
                    lastName: 'Name',
                }
            }).as('getTeacherDetail');

            cy.contains('button', 'Detail').click();
            cy.contains('button', 'Delete').click();

            cy.wait('@deleteSession');

            // Check if navigation to sessions list or relevant page happens
            cy.location().should((loc) => {
                expect(loc.href).to.include('/sessions');
            });
        });
        it('access to session detail page', () => {
            cy.url().should('include', '/sessions');
            
            cy.intercept('GET', '/api/session/1', {
              body: {
                id: 1,
                name: 'Meditation Techniques',
                description: 'A session on various meditation techniques',
                date: '2024-07-30T10:00:00.000Z',
                teacher_id: 2,
                users: []
              }
            },);
        
            cy.intercept('GET', '/api/teacher', {
              body: [
                {
                  id: 2,
                  firstName: 'Meditation',
                  lastName: 'Techniques',
                }
              ]
            },);
    
            cy.contains('Detail').click();

            cy.visit('/sessions');
        });
        it('access to session Edit page', () => {
            cy.get('input[formControlName=email]').type("yoga@studio.com");
            cy.get('input[formControlName=password]').type("yourPassword");
            cy.get('button[type=submit]').click();
            cy.url().should('include', '/sessions');
            
            cy.intercept('GET', '/api/session/1', {
              body: {
                id: 1,
                name: 'Meditation Techniques',
                description: 'A session on various meditation techniques',
                date: '2024-07-30T10:00:00.000Z',
                teacher_id: 2,
                users: []
              }
            },);
        
            cy.intercept('GET', '/api/teacher', {
              body: [
                {
                  id: 2,
                  firstName: 'Meditation',
                  lastName: 'Techniques',
                }
              ]
            },);
    
            cy.contains('Edit').click();

            cy.visit('/sessions');
        });
        it('update session successful', () => {
            cy.get('input[formControlName=email]').type("yoga@studio.com");
            cy.get('input[formControlName=password]').type("yourPassword");
            cy.get('button[type=submit]').click();
            cy.url().should('include', '/sessions');
            cy.intercept('GET', '/api/session/1', {
              body: {
                id: 1,
                name: 'Meditation Techniques',
                description: 'A session on various meditation techniques',
                date: '2024-07-30T10:00:00.000Z',
                teacher_id: 2,
                users: []
              }
            },);
        
            cy.intercept('GET', '/api/teacher', {
              body: [
                {
                  id: 2,
                  firstName: 'Meditation',
                  lastName: 'Techniques',
                }
              ]
            },);

            cy.intercept('PUT', '/api/session/1', {
                body: {
                  id: 1,
                  name: 'Beginner Workout',
                  description: 'New session for noobs!',
                  date: '2023-05-15T00:00:00.000+00:00',
                  teacher_id: 1
                },
              }).as('updateSession');
      
            cy.contains('button', 'Edit').click();
      
            cy.get('input[formControlName=name]').clear().type(`${"Stretching new session"}`);
            cy.get('input[formControlName=date]').clear().type(`${"2024-11-30"}`);
            cy.get('textarea[formControlName=description]').clear().type(`${"A stretching session"}`);
      
            cy.intercept('GET', '/api/session', {
                body: [
                  {
                    id: 1,
                    name: 'Stretching',
                    date: '2023-12-30T00:00:00.000+00:00',
                    teacher_id: 1,
                    description: 'A stretching session',
                    users: []
                  }
                ]
              },);
              cy.get('button[type=submit]').click();
            });

            it('delete session', () => {
                cy.intercept('GET', '/api/session/1', {
                    body: {
                      id: 1,
                      name: 'Meditation Techniques',
                      description: 'A session on various meditation techniques',
                      date: '2024-07-30T10:00:00.000Z',
                      teacher_id: 2,
                      users: []
                    }
                  },);
              
                  cy.intercept('GET', '/api/teacher', {
                    body: [
                      {
                        id: 2,
                        firstName: 'Meditation',
                        lastName: 'Techniques',
                      }
                    ]
                  },);
          
                  cy.contains('Detail').click();

                cy.contains('button', 'Delete').click();

            });
       
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
                cy.get('input[formControlName=password]').type("yourPassword");
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

            }
            );



            it('access to me page ', () => {
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
                cy.get('input[formControlName=password]').type("yourPassword");
                cy.get('button[type=submit]').click();

                // Attendre que la requête de login soit complétée
                cy.wait('@loginRequest');

                // Vérifier que l'utilisateur est connecté (ajoutez une vérification selon votre application)
                // Par exemple, vérifier la présence d'un élément spécifique à la page après connexion
                cy.contains('Account').should('be.visible');

                // Cliquer sur le bouton de déconnexion
                cy.contains('Account').click();

                // Intercepter la requête de déconnexion si elle existe et attendre sa réponse
                cy.intercept('POST', '/api/me', {
                    statusCode: 200
                }).as('logoutRequest');

                cy.visit('/login');
                
            }
            );
            it('try to session page without login', () => {
                cy.visit('/sessions');
                cy.url().should('include', '/login');
            });
            it('try to delete session without login', () => {
                cy.intercept('GET', '/api/session/1', {
                    body: {
                      id: 1,
                      name: 'Meditation Techniques',
                      description: 'A session on various meditation techniques',
                      date: '2024-07-30T10:00:00.000Z',
                      teacher_id: 2,
                      users: []
                    }
                  },);
              
                  cy.intercept('GET', '/api/teacher', {
                    body: [
                      {
                        id: 2,
                        firstName: 'Meditation',
                        lastName: 'Techniques',
                      }
                    ]
                  },);
                cy.visit('/session/1');
                
            });

            it('try to update session without login', () => {
                cy.intercept('GET', '/api/session/1', {
                    body: {
                      id: 1,
                      name: 'Meditation Techniques',
                      description: 'A session on various meditation techniques',
                      date: '2024-07-30T10:00:00.000Z',
                      teacher_id: 2,
                      users: []
                    }
                  },);
              
                  cy.intercept('GET', '/api/teacher', {
                    body: [
                      {
                        id: 2,
                        firstName: 'Meditation',
                        lastName: 'Techniques',
                      }
                    ]
                  },);
                cy.visit('/session/1');
                
            });
            it('try to create session with out teacher', () => {
                cy.visit('/login');
                cy.get('input[formControlName=email]').type("yoga@studio.com");
                cy.get('input[formControlName=password]').type("yourPassword");
                cy.get('button[type=submit]').click();
                
                cy.wait('@login');
                cy.wait('@session');

                cy.intercept('GET', '/api/teacher', {
                    body: []
                }).as('getTeachers');

                cy.contains('button', 'Create').click();

                cy.get('input[formControlName=name]').type("totoekfe");
                cy.get('input[formControlName=date]').type("2024-07-26");
                cy.get('textarea[formControlName=description]').type("the best session ever");
                cy.get('button[type=submit]').should('be.disabled');
            });
            it('edit not found session', () => {
                cy.visit('/login');
        
                cy.get('input[formControlName=email]').type("yoga@studio.com");
                cy.get('input[formControlName=password]').type("yourPassword");
                cy.get('button[type=submit]').click();
        
                cy.wait('@login');
                cy.wait('@session');
        
                cy.intercept('GET', '/api/session/999', {
                    statusCode: 404,
                    body: {
                        error: "Page not found !"
                    }
                }).as('getSessionDetail');
        
                cy.visit('/session'); // Assurez-vous que '/sessions/999' correspond à votre URL de test
            });
            
    

    
});
