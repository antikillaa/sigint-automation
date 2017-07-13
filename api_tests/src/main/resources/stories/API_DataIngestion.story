Meta:
@story ingestion

Scenario: API.Ingestion with the wrong source type
Meta: @nightly
Given I sign in as admin user
And Data source with F and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Request is successful
When I send get file meta request
Then Message contains "file with id"

Examples:
| sourceType | recordType | recordsCount |
| S          | SMS        | 10           |


Scenario: Upload several files with the same name into one source
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files

When I upload data files
Then I got response code 409
And Message contains "FileAlreadyExistsException"

Examples:
| sourceType | recordType | recordsCount |
| F          | SMS        | 10           |



Scenario: API.F-SMS ingestion
Meta: @nightly @F-SMS
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| F          | SMS        | 100          |



Scenario: API.Phonebook ingestion
Meta: @nightly @Phonebook
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| PHONEBOOK  | Phonebook  | 100          |



Scenario: API.ETISALAT-Subscriber format1 ingestion
Meta: @nightly @E-Subscriber1
Given I sign in as admin user
And Data source with <sourceType> and Subscriber exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType  | recordsCount |
| ETISALAT   | Subscriber1 | 100          |


Scenario: API.ETISALAT-Subscriber format2 ingestion
Meta: @nightly @E-Subscriber2
Given I sign in as admin user
And Data source with <sourceType> and Subscriber exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType  | recordsCount |
| ETISALAT   | Subscriber2 | 100          |


Scenario: API.ETISALAT-SMS format2 ingestion
Meta: @nightly @E-SMS2
Given I sign in as admin user
And Data source with <sourceType> and SMS exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| ETISALAT   | SMS2       | 100          |


Scenario: API.ETISALAT-SMS format3 ingestion
Meta: @nightly @E-SMS3
Given I sign in as admin user
And Data source with <sourceType> and SMS exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| ETISALAT   | SMS3       | 100          |


Scenario: API.ETISALAT-SMS format4 ingestion
Meta: @nightly @E-SMS4
Given I sign in as admin user
And Data source with <sourceType> and SMS exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| ETISALAT   | SMS4       | 100          |


Scenario: API.ETISALAT-CDR ingestion
Meta: @nightly @E-CDR
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| ETISALAT   | CDR        | 100          |



Scenario: API.S-Voice ingestion
Meta: @nightly @S-Voice
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload audio files
When I upload data files
Then Uploaded audio files are processed
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | Voice      | 10           |


Scenario: API.S-Fax ingestion
Meta: @nightly @S-Fax
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | Fax        | 100          |


Scenario: API.S-SMS ingestion
Meta: @nightly @S-SMS
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | SMS        | 100          |


Scenario: API.S-VLR ingestion
Meta: @nightly @S-VLR
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | VLR        | 100          |


Scenario: API.S-CDR ingestion
Meta: @nightly @S-CDR
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | CDR        | 100          |


Scenario: API.S-CELL ingestion
Meta: @nightly @S-CELL
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | CELL       | 100          |



Scenario: API.T-SMS ingestion
Meta: @nightly @T-SMS
Given I sign in as admin user
And Data source with <sourceType> and T exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload data files
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| T          | SMS        | 100          |


Scenario: API.T-Voice ingestion
Meta: @nightly @T-Voice
Given I sign in as admin user
And Data source with <sourceType> and T exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And I create remote path for ingestion
When I upload audio files
When I upload data files
Then Uploaded audio files are processed
Then Uploaded data files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| T          | Voice      | 10           |



Scenario: API.Search and count data folders by path
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
When I search data folders by source with date pattern "yyyy/MM"
Then Found folders list size is more than 0
When I send count found folders request
Then Folders count result is equal to appropriate list size

Examples:
| sourceType | recordType |
| T          | T          |
| F          | SMS        |
| S          | SMS        |


Scenario: API.Search and count data folders by wildcard
Meta: @nightly
Given I sign in as admin user
When I search data folders with date pattern "*/yyyy"
Then Found folders list size is more than 0
When I send count found folders request
Then Folders count result is equal to appropriate list size


Scenario: API.Search and count data files by path
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
When I search data folders by source with date pattern "yyyy/MM/dd"
Then Found folders list size is more than 0
When I search data files by path
Then Found files list size is more than 0
When I send count found files request
Then Files count result is equal to appropriate list size

Examples:
| sourceType | recordType |
| T          | T          |
| F          | SMS        |
| S          | SMS        |


Scenario: API.Search and count data files by wildcard
Meta: @nightly
Given I sign in as admin user
When I search data files with date pattern "*/yyyy/MM/dd/*"
Then Found files list size is more than 0
When I send count found files request
Then Files count result is equal to appropriate list size