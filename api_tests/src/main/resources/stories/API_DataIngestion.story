Meta:
component ingestion


Scenario: API.Generate SSMS
Meta:
@TEEL
Given I sign in as admin user
And Generate 3 SSMS


Scenario: API.S-SMS ingestion. Target matching by phone number, name and keywords.
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And <targetCount> targets with phones generated and added
And <sourceType> - <recordType> data file with records for test targets was generated
When I send upload <sourceType> - <recordType> data file request
Then I got response code 201
When uploaded file is processed
And ingest matching complete
Then search results contain correct counts: total records, target hits and mentions
When I send get upload details request
Then I got response code 200
And matching results contain correct Summary: total records, target hits and mentions
And matching results contain correct Matching results: total hit&Mention records, list of hit/mention targets with hit/mention records counts for each

Examples:
| sourceType | recordType | targetCount |
| Strategic  | SMS        | 2           |


Scenario: API.X-SMS ingestion. Target matching by phone number, name and keywords.
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And <targetCount> targets with phones generated and added
And <sourceType> - <recordType> data file with records for test targets was generated
When I send upload <sourceType> - <recordType> data file request
Then I got response code 201
When uploaded file is processed
And ingest matching complete
Then search results contain correct counts: total records, target hits and mentions
When I send get upload details request
Then I got response code 200
And matching results contain correct Summary: total records, target hits and mentions
And matching results contain correct Matching results: total hit&Mention records, list of hit/mention targets with hit/mention records counts for each

Examples:
| sourceType | recordType | targetCount |
| X          | SMS        | 2           |

Scenario: API.X-VoiceMetadata ingestion. Target matching by phone number, name and keywords.
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And <targetCount> targets with phones generated and added
And <sourceType> - <recordType> data file with records for test targets was generated
When I send upload <sourceType> - <recordType> data file request
Then I got response code 201
When uploaded file is processed
And ingest matching complete
Then search results contain correct counts: total records, target hits and mentions
When I send get upload details request
Then I got response code 200
And matching results contain correct Summary: total records, target hits and mentions
And matching results contain correct Matching results: total hit&Mention records, list of hit/mention targets with hit/mention records counts for each

Examples:
| sourceType | recordType | targetCount |
| X          | Voice      | 2           |