Meta:
@story designation-mappings

Lifecycle:
Before:
Given I sign in as admin user
When I send create new random designation-mapping request
Then Request is successful
And Designation-mapping is correct
After:
Given I sign in as admin user
When I send delete designation-mapping request
Then Request is successful

Scenario: API.Create designation-mapping
When I send get list of designation-mappings request
Then Request is successful
Then Designation-mappings list size is more than 0
Then Designation-mapping is in list

Scenario: API.Get designation-mapping by id
When I send view designation-mapping request
Then Request is successful
And Designation-mapping is correct

Scenario: API.Update designation-mapping
When I send update designation-mapping request
Then Request is successful
And Designation-mapping is correct

Scenario: API.Delete designation-mapping
When I send view designation-mapping request
Then Request is successful

Scenario: API.Get designation-mappings list
When I send get list of designation-mappings request
Then Request is successful
And Designation-mappings list size is more than 0

Scenario: API.Search designation-mappings by filters
When I send search designation-mappings by <criteria> with <value> request
Then Request is successful
And Designation-mappings list size  is more than 0
And Designation-mappings search result is correct

Examples:
| criteria      | value  |
| identifier    | random |
| type          | random |
| designation   | random |
| updatedAfter  | random |
| empty         |        |

Scenario: API. Import designation-mappings
Given I generate 5 random designation-mappings
And I write designation-mappings to CSV with header
When I send import designation-mappings request
Then Request is successful
And 5 designation-mappings are imported
And I delete designation-mappings

Scenario: Import non .csv file for designation-mapping records
Given T - SMS data file with 10 records was generated
When I send import designation-mappings request
Then I got response code 400
And Message contains "Unable to import non csv files"

Scenario: Import designation-mappings with duplicate identifiers but different designations
Given I generate 1 random designation-mappings
And I add the same designation-mapping with "Another designation" designation to the list
And I write designation-mappings to CSV with header
When I send import designation-mappings request
Then Request is successful
And 1 designation-mappings are imported
And I delete designation-mappings

Scenario: API. Import designation-mappings with duplicate values
Given I generate 1 random designation-mappings
And I write designation-mappings to CSV with header
When I send import designation-mappings request
Then Request is successful
And 1 designation-mappings are imported
When I send import designation-mappings request
Then Request is successful
And 0 designation-mappings are imported
And Message contains "already exists"

Scenario: API. Import designation-mappings with errors in file
Given I generate 1 random designation-mappings
And I add broken designation-mapping to the list
And I write designation-mappings to CSV with header
When I send import designation-mappings request
Then Request is successful
And Message contains "Designation is not set"
And 1 designation-mappings are imported
And I delete designation-mappings