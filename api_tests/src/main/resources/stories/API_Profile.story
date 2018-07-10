Meta:
@story profile @stage

Lifecycle:
Before:
Given I sign in as admin user

Scenario: Find in system or create test target from json
Given Find or create test target from json:<target>

Examples:
| target | image |
| profiles/Darkwing_Duck.json | profiles/Darkwing_Duck.jpg |
| profiles/Launchpad_McQuack.json | profiles/Launchpad_McQuack.jpg |


Scenario: Upload image to profiles
Given Find or create test target from json:<target>
When upload new target image:<image> to target
Then Request is successful
When I send get profile details request
Then Profile contain uploaded image

Examples:
| target | image |
| profiles/Darkwing_Duck.json | profiles/Darkwing_Duck.jpg |
| profiles/Launchpad_McQuack.json | profiles/Launchpad_McQuack.jpg |


Scenario: API.Create profile
When I send create finder file request
Then Request is successful
When I send create profile request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete finder file request
Then Request is successful


Scenario: API.Deleting of profile
When I send create finder file request
Then Request is successful
When I send create profile request
Then Request is successful

When I send get profile details request
Then Request is successful

When I send delete profile request
Then Request is successful
When I send delete finder file request
Then Request is successful

Scenario: API.Merge two profiles into one
Meta: @skip
When I send create finder file request
Then Request is successful
When I send create profile request
Then Request is successful
When I send create profile request
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
When I send delete finder file request
Then Request is successful


Scenario: Add hit(s) for existing targets
Given I clean up ingestion directory
!-- find first profiler
When I search finder file members by query:<target>
Then Request is successful
When Get profiles from CBFinder search results
Then Profile list size > 0
When get random profile from profile list
And I send get profile details request
And send get profile identifierAggregations request
!-- add targets identifiers to injection file
And add <recordsCount> <identifierType> from profile:<target> to injection file
!-- data ingestion
Given Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> files with <recordsCount> records are generated
And I create remote path for ingestion
When I upload files
Then Uploaded files are processed
And Original data file is searchable within the system
And Number of ingested event records in CB == <recordsCount>
!-- verification
Then identifierAggregations hit counts for:<identifierType> of profile:<target> should incremented

Examples:
| target | identifierType | sourceType | recordType | recordsCount |
| Darkwing Duck, Drake Mallard | PHONE_NUMBER  | S | SMS | 1 |
| Darkwing Duck, Drake Mallard | IMEI | T | SMS | 1 |
| Darkwing Duck, Drake Mallard | IMSI | F | SMS | 1 |


Scenario: Get profile summary
When I send create finder file request
Then Request is successful
When I send create profile request
Then Request is successful

When I send get profile summary request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete finder file request
Then Request is successful


Scenario: VoicePrint from manual audio
Meta: @notstage
When I send create finder file request
Then Request is successful
When I send create profile request
Then Request is successful

Given S - Voice files with 1 records are generated

When upload audio file to profile
Then uploaded audio file is processed
When get voice events from profile
When create voiceID for profile
When I send get profile details request
Then profile contain created voiceID
