Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: CB Finder classification level verification
Given I sign in as admin user
Given I sign in as user with classifications: <classifications>
When I send get root list of CBFinder request
Then CB Finder entities list size > 0
Then CBFinder return items only for classification level: <classifications>

Examples:
| classifications |
| S |