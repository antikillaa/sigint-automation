Meta:
@story login

Scenario: On Login request with correct credentials token should be sent
When I sent sign in request as admin user with correct credentials
Then Request is successful


Scenario: User got error on sign in request with incorrect credentials
When I sent sign in request as admin user with incorrect credentials
Then Request is unsuccessful
And Error message is invalid username or password
