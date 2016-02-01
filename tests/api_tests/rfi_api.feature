
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


  Scenario: Operator can find RFI searching by Max Request Date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate |
         | 2015-01-24  |
    Then I can find RFI using specific search request
         |max_created_date|
         |2015-01-24      |

  Scenario: Operator can find RFI searching by min Request Date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate |
         | 2015-01-24  |

    Then I can find RFI using specific search request
         |min_created_date|
         |2015-01-24      |

  @wip
  Scenario: Operator can find RFI searching by status
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | state       |
         | ASSIGNED    |

    Then I can find RFI using specific search request
         |state           |
         |ASSIGNED        |
