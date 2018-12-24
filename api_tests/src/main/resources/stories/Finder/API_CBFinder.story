Meta:
@API
@component Finder
@story CBFinder
@stage

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Lifecycle:
Before:
Given I sign in as admin user


Scenario: CB Finder classification permissions
Given I sign in as user with classifications: <classifications>
When I send get root list of CBFinder request
Then CB Finder entities list size > 0
Then CBFinder return items only for classifications: <classifications>

Examples:
| classifications |
| S |


Scenario: CB Finder orgUnits permissions
Given I sign in as user with orgUnits: <orgunits>
When I send get root list of CBFinder request
Then CB Finder entities list size > 0
Then CBFinder should return items regarding current user orgUnits

Examples:
| orgunits |
| QA auto team |