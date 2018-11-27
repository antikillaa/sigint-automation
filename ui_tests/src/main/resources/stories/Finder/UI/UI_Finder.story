
Lifecycle:
Before:
Given load story ../../aux-main-stories/auxSignIn.story with example table:
data/QE_login.table



Scenario: Verfiy all data sources
Given I navigate to Search

Given I open Search Filter on the Search page
Given I set Search Tab to General on the Search Filter page
Given I set Source Type to Profiler on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to Documents on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to SIGINT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to EID on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to GOVINT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to FININT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to Traffic on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to OSINT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to CIO on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view



Scenario: Verfiy all Record Type for Documnets
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set Source Type to Documents on the Search Filter page
Given I set Record Type to (|<RecordType>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|RecordType|
|Operator Report|
|Request For Approval|


Scenario: Verfiy all data sources for SIGINT
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set date (01/01/2016) as Earliest Event Time on the Search Filter page

Given I set Source Type to SIGINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|du|
|Etisalat|
|F|
|J1|
|J2|
|Manual|
|O|
|Phonebook|
|S|
|T|


Scenario: Verfiy all data and Record Type for SIGINT
Meta:@fix1
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set date (01/01/2016) as Earliest Event Time on the Search Filter page

Given I set Source Type to SIGINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I set Record Type to (|<RecordType>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource||RecordType|
|du||Call|
|du||Domestic Movement|
|du||International Roaming|
|du||Subscribe|
|du||Texting event|
|Etisalat||Call|
|Etisalat||Domestic Movement|
|Etisalat||International Roaming|
|Etisalat||Subscriber|
|Etisalat||Texting event|
|J2||Call|
|J2||Texting|
|Manual||Call|
|Manual||E-mail|
|Manual||Fax|
|Manual||Texting event|
|O||Call|
|O||E-mail|
|O||International Roaming|
|O||Fax|
|O||Texting event|
|S||Call|
|S||E-mail|
|S||International Roaming|
|S||Fax|
|S||Texting event|
|S||VOIP|
|T||Call|
|T||Texting|


Scenario: Verfiy all data sources for SIGINT Event
Given I navigate to Search
Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I set Source Type to SIGINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|du|
|Etisalat|
|F|
|J1|
|J2|
|Manual|
|O|
|Phonebook|
|S|
|T|



Scenario: Verfiy all data sources for SIGINT Entity
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set Source Type to SIGINT on the Search Filter page
Given I set Object Type to Entity on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|du|
|Etisalat|
|F|
|J1|
|J2|
|Manual|
|O|
|Phonebook|
|S|
|T|


Scenario: Verfiy all data sources for GOVINT Event
Given I navigate to Search
Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I set Source Type to GOVINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|SITA|
|UDB|

Scenario: Verfiy all data sources for GOVINT Entity
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set Source Type to GOVINT on the Search Filter page
Given I set Object Type to Entity on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|SITA|
|UDB|


Scenario: Verfiy all data sources for GOVINT
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set date (01/01/2016) as Earliest Event Time on the Search Filter page
Given I set Source Type to GOVINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|SITA|
|UDB|









Scenario: Verfiy all data sources for EID Entity
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set Source Type to EID on the Search Filter page
Given I set Object Type to Entity on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|Passport|
|Person|
|Visa|


Scenario: Verfiy all data sources for EID
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set date (01/01/2016) as Earliest Event Time on the Search Filter page
Given I set Source Type to GOVINT on the Search Filter page
Given I set Data Source to (|<DataSource>|) on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Examples:
|DataSource|
|Passport|
|Person|
|Visa|
|EID Investigation|


