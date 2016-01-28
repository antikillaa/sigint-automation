
Feature: Users can create, update, delete RFI records based on permissions


  Scenario: Operator user can create new RFI request
    When I signed in as "tasker" user
    And  I create new RFI with default values
    Then New information request record is created


  Scenario: Operator user can update RFI with files
    When I signed in as "tasker" user
    And  I create new RFI with default values
    Then New information request record is created
    When I update record with files
    Then Information request record has attached files

  @wip
  Scenario: Operator can find RFI searching by Request Date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate |
         | 24.01.2015   |
    Then I can find RFI using specific search request
         |max_created_date|
         |2015-01-24      |
