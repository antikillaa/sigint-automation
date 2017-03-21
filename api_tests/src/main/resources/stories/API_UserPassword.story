Test user password strength validation.
Parent story: CB-2407

Meta:
@component userPassword

Lifecycle:
Before:
Given I sign in as admin user
When I send create a new user
Then Request is successful
After:
Given I sign in as admin user
When I send delete user request
Then Request is successful
When I send sign out request
Then Request is successful

Scenario: API.Check short password
When I change user password to 123456
Then I got response code 400
And Message contains "at least 8"

Scenario: API.Check long weak password
When I change user password to LONGPASSWORDHERE
Then I got response code 400
And Message contains "too weak"

Scenario: API.Check dictionary password
When I change user password to P@ssWord1
Then I got response code 400
And Message contains "too weak"

Scenario: API.Check password with more than two successive identical characters
When I change user password to My-very-G00d-paswooord
Then I got response code 400
And Message contains "more than 2 repeated"

Scenario: API.Check random 8-character alpha-numeric password
When I change user password to random
Then I got response code 200

Scenario: API.Check password with username
When I change user password to username
Then I got response code 400
And Message contains "not valid"

Scenario: API.Check password with emoji symbols
When I change user password to Em0j1🚀🚃🚑🚕
Then I got response code 400

Scenario: API.Check that password is different from the last 3 passwords used
When I change user password to This-happened-once-before
Then Request is successful
When I change user password to When-I-came-to-your-door
Then Request is successful
When I change user password to They-said-it-wasn't-you
Then Request is successful
When I change user password to But-I-saw-you-peep-through
Then Request is successful

When I change user password to When-I-came-to-your-door
Then I got response code 400
And Message contains "must be different"
When I change user password to They-said-it-wasn't-you
Then I got response code 400
And Message contains "must be different"
When I change user password to But-I-saw-you-peep-through
Then I got response code 400
And Message contains "must be different"

When I change user password to This-happened-once-before
Then Request is successful

Scenario: API.Check that user will be locked after 5 sequential failed login attempts
When I set wrong user password
And I send sign out request
Then Request is successful

When I sign in as new created user
Then I got response code 400
When I sign in as new created user
Then I got response code 400
When I sign in as new created user
Then I got response code 400
When I sign in as new created user
Then I got response code 400
When I sign in as new created user
Then I got response code 400

When I sign in as new created user
Then I got response code 403
And Message contains "Access Denied"