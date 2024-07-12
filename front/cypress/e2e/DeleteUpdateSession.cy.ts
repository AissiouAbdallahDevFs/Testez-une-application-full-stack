describe('Delete Update Session Management', () => {

    describe('Login spec', () => {
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

        it('update session successful', () => {
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
    });

    
});
