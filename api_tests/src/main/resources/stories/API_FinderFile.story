Meta:
@story finderFile

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful
After:
ANY
When I send delete finder file request
Then Request is successful


Scenario: API.Create and delete of finder file
Then Created finder file is correct


Scenario: API.Get list of finder files
When I send get list of finder file request
Then Request is successful
Then Created finder file in list


Scenario: API.Addition of new child finder file
When I send create new child finder file request
Then Request is successful

When I send get finder file details request
Then Request is successful
Then created nested finder file is correct

When I send get content of parent finder file
Then Request is successful
Then Finder file content contains created nested file

When I send delete finder file request
Then Request is successful


Scenario: API.Get contents of file
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I add profile draft to finder file
When I send publish profile draft request
Then Request is successful

When I send create new child finder file request
Then Request is successful
When I send get finder file details request
Then Request is successful
Then created nested finder file is correct

When I send get content of parent finder file
Then Request is successful
Then Finder file content contains created nested file
And Finder file content contains created profile

When I send delete profile request
Then Request is successful
When I send delete finder file request
Then Request is successful
