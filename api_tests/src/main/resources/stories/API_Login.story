
Scenario: On Login request with correct credentials token should be sent
When I sent sign in request as admin user with correct credentials
Then I got response code 200


Scenario: User got error on sign in request with incorrect credentials
When I sent sign in request as admin user with incorrect credentials
Then I got response code 400
And Error message is invalid username or password


