Meta:

Narrative:
CB-11288 Cases CRUD
1. User can create a case with the following attributes:
Case Name. Required.
Description. Optional.
File. Required.
Created on. Auto-populated.
Created by. Auto-populated.
Classification. Required.
OU. Required. [Hardcoded value for the scope of this story]
2. Navigation entry points as per https://confluence.pegasus.ae/display/INTEL/Crystal+Ball+Navigation+Components
3. User can edit a case.
4. User can delete an empty case.
5. A case can belong to only one file
6. A case must be the leaf node in a file hierarchy in the sense that a case cannot contain files or other cases.

Lifecycle:
Before:
Given I sign in as user with permissions FILE_CREATE, FILE_VIEW, CASE_CREATE, CASE_VIEW
When I send create finder file request
Then Request is successful
Then Created finder file is correct

Scenario: User can create a case
Meta: @wip2
When I send create finder case request
Then Request is successful
Then Created finder case is correct
