Meta:
@story profile

Lifecycle:
Before:
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
Then Profile draft is correct

When I send create target group request
Then Request is successful
And Created target group is correct
When I add profile draft to target group

When I send publish profile draft request
Then Request is successful


Scenario: API.Publishing of new profile draft
When I send get profile details request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: Deleting of profile
When I send get profile details request
Then Request is successful

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful

Scenario: API.Merge two profiles into one
When I send create profile draft request
Then Request is successful
When I add profile draft to target group
When I send publish profile draft request
Then Request is successful

When I send merge two profile into one request
Then Request is successful

When I send get profile draft details request
Then Request is successful
And Merged profile draft is correct

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct

When I send get first merged profile details request
Then Request is unsuccessful
When I send get second merged profile details request
Then Request is unsuccessful

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: Add hit(s) for existing targets
!-- find first profiler
When I search target group members by query:<to_target>
Then Request is successful
When Get profiles from targets search results
Then Profile list size > 0
When get random profile from profile list
And I send get profile details request
And send get profile identifierAggregations request
!-- find second profiler
When I search target group members by query:<from_target>
Then Request is successful
When Get profiles from targets search results
Then Profile list size > 0
When get random profile from profile list
When I send get profile details request
And send get profile identifierAggregations request
!-- add targets identifiers to injection file
And add <recordsCount> <identifierType> from profile:<to_target> to injection file
And add <recordsCount> <identifierType> from profile:<from_target> to injection file
!-- data ingestion
Given Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> files with <recordsCount> records are generated
And I create remote path for ingestion
When I upload files
Then Uploaded files are processed
And Original data file is searchable within the system
And Number of ingested event records in CB == <recordsCount>
!-- verification
Then identifierAggregations hit counts for:<identifierType> of profile:<to_target> should incremented
Then identifierAggregations hit counts for:<identifierType> of profile:<from_target> should incremented

Examples:
| to_target  | from_target | identifierType | sourceType | recordType | recordsCount |
| adubatovka | alexd friend | PHONE_NUMBER  | S          | SMS        | 1            |


Scenario: Get profile summary
When I send get profile summary request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: API.GetORCreate profile
Meta: @skip
When I send getOrCreate profile request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: Load test target
Meta: @skip
Given Create test target from json:<target>

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful

Examples:
| target |
| profiles/test_target.json |


