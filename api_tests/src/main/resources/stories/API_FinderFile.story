Meta:
@story finderFile

Narrative:
CB-12319 File Permissions integration
FE/BE: To view or check existence of a file, a user must have FILE_VIEW permission
FE/BE: To create a file, a user must have FILE_CREATE permissions
FE/BE: To update a file, a user must have FILE_UPDATE permissions
FE/BE To delete a file, a user must have FILE_DELETE permissions

FE: User will see CB Finder in menu only when he has FILE_VIEW permission
BE: Unauthenticated users cannot access any file-system endpoint
BE: All file endpoints will only return files that the user is authorized to see

Scenario: API.To view or check existence of a file, a user must have FILE_VIEW permission
Given I sign in as user with permissions FILE_VIEW
When I send get list of finder file request
Then Request is successful
Then Finder files list size > 0

Given I sign in as user with permissions UM_USER
When I send get list of finder file request
Then Request is unsuccessful


Scenario: API.To create a file, a user must have FILE_CREATE permissions
Given I sign in as user with permissions FILE_CREATE
When I send create finder file request
Then Request is successful
Then Created finder file is correct

Given I sign in as user with permissions UM_USER
When I send create finder file request
Then Request is unsuccessful


Scenario: API.To update a file, a user must have FILE_UPDATE permissions
Given I sign in as user with permissions FILE_CREATE
When I send create finder file request
Then Request is successful
Then Created finder file is correct
When I send update finder file request
Then Request is unsuccessful

Given I sign in as user with permissions FILE_VIEW, FILE_UPDATE
When I send update finder file request
Then Request is successful
Then Created finder file is correct


Scenario: API.To delete a file, a user must have FILE_DELETE permissions
Given I sign in as user with permissions FILE_VIEW, FILE_UPDATE, FILE_CREATE
When I send CB Finder search with query:<query>
Then Request is successful
When Get files from CBFinder search results
Then Finder files list size > 0
When Get random file from from CBFinder search results
And I send delete finder file request
Then Request is unsuccessful

Given I sign in as user with permissions FILE_VIEW, FILE_DELETE
When I send CB Finder search with query:<query>
Then Request is successful
When Get files from CBFinder search results
Then Finder files list size > 0
When I delete all empty QE auto files
Then Request is successful

Examples:
| query |
| QE auto |


Scenario: API.Addition of new child finder file
Given I sign in as admin user
When I send create finder file request
Then Request is successful

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
When I send delete finder file request
Then Request is successful


Scenario: API.Get contents of file
Given I sign in as admin user
When I send create finder file request
Then Request is successful

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

When I send delete finder file request
Then Request is successful