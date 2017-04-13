Test user password strength validation.
Parent story: CB-2407

Meta:
@story userPassword

Lifecycle:
Before:
Given I sign in as admin user
When I send create a new user
Then Request is successful

Scenario: API.Check short password
Meta: @skip
When I change user password to 123456
Then I got response code 400
And Message contains "at least 8"

Scenario: API.Check long weak password
Meta: @skip
When I change user password to LONGPASSWORDHERE
Then I got response code 400
And Message contains "not valid"

Scenario: API.Check dictionary password
Meta: @skip
When I change user password to P@ssWord1
Then I got response code 400
And Message contains "too weak"

Scenario: API.Check password with more than two successive identical characters
Meta: @skip
When I change user password to My-very-G00d-paswooord
Then I got response code 400
And Message contains "more than 2 repeated"

Scenario: API.Check random password
Meta: @skip
!-- upper and lower case letters, and digits
When I change user password to random
Then I got response code 200

!-- upper and lower case letters
When I change user password to adoKSLAQxo
Then I got response code 200

!-- lower case letters and digits
When I change user password to 9h21b33wx0
Then I got response code 200

!-- upper case letters and digits
When I change user password to K9JJ782DXO
Then I got response code 200

!-- lower case letters and special characters
When I change user password to adoksl@q!@
Then I got response code 200

!-- upper case letters and special characters
When I change user password to HDK@HD!D&O
Then I got response code 200

!-- digits and special characters
When I change user password to 324@!421#$
Then I got response code 200

Scenario: API.Check password with username
Meta: @skip
When I change user password to username
Then I got response code 400
And Message contains "not valid"

Scenario: API.Check password with emoji symbols
Meta: @skip
When I change user password to Em0j1🚀🚃🚑🚕
Then I got response code 200

Scenario: API.Check that password is different from the last 3 passwords used
Meta: @skip
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
Meta: @skip
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
And Message contains "AUTH_FORBIDDEN"