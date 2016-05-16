Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal


Scenario: On Login request with correct credentials token should be sent
Given as admin user
When I sent sign in request with correct credentials
Then I got response code 200
And I got token in response


Scenario: User got error on sign in request with incorrect credentials
Given as admin user
When I sent sign in request with incorrect credentials
Then I got response code 400
And Error message is invalid username or password


