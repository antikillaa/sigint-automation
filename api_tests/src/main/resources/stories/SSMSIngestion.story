Meta:
component ingestion


Scenario: API.Generate SSMS
Meta:
@TEEL
Given I sign in as admin user
And Generate 3 SSMS


Scenario: API.Check matching results
Meta:
@wip
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And targets with phones exists
And <sourceType> - <recordType> data file with: total <totalRecords> records, <numToTarget> of them toTarget, <numFromTarget> of them fromTarget was generated
When I send upload <sourceType> - <recordType> data file request
Then I got response code 201
When uploaded file is processed
And ingest matching complete
Then search results contain <totalRecords> total records with: <targetHitCount> at least hits number and <mentionCount> at least mention number
When I send get upload details request
Then I got response code 200
And matching results contain <targetHitCount> hits number and <recordHitCount> matched records and have HIT target result type

Examples:
| sourceType | recordType | totalRecords | numToTarget | numFromTarget | targetHitCount | recordHitCount | mentionCount |
| Strategic  | SMS        | 3            | 1           | 1             | 1              | 2              | 0            |
