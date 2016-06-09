Meta:
@component phonebook


Scenario: API.Create PhoneBook with all fields
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200
And Phonebook Entry is correct


Scenario: API.Get details of Phonebook Entry
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200
When I get details of created Phonebook Entry
Then Phonebook Entry is correct


Scenario: API.Update existing Phonebook
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200
When I send update request for created Phonebook Entry
Then I got response code 200
And Phonebook Entry is correct


Scenario: API.Deleting existing Phonebook
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200
When I send delete request for created Phonebook Entry
Then I got response code 200
And Phonebook Entry was deleted


Scenario: API.User can find Phonebook Entry using search filters
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200
When I search Phonebook Entry by <criteria> and value <value>
Then Search Phonebook results are correct
And Searched Phonebook Entry in list

Examples:
| criteria    | value  |
| address     | random |
| name        | random |
| imsi        | random |
| phoneNumber | random |
| country     | random |
| countryCode | random |


Scenario: API.Upload DuSubscriber Entry with all fields
Given I sign in as admin user
When I send upload DuSubscriberEntry request with all fields
Then I got response code 200
And DuSubscriberEntry upload result is successful
When I send search DuSubscribers by phoneNumber and value random
Then DuSubscriber search result are correct
And Searched DuSubscriber Entry in list


Scenario: API.Search DuSubscriber Entry using search filters
Meta:
@TEEL 1787
Given I sign in as admin user
When I send upload DuSubscriberEntry request with all fields
Then I got response code 200
And DuSubscriberEntry upload result is successful
When I send search DuSubscribers by <criteria> and value <value>
Then DuSubscriber search result are correct
And Searched DuSubscriber Entry in list

Examples:
| criteria    | value  |
| address     | random |
| name        | random |
| phoneNumber | random |