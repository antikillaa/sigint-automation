
Feature: Users can create, update, delete RFI records based on permissions

  @stable
  Scenario: Operator user can create new RFI request
    When I signed in as "tasker" user
    And  I create new RFI with default values
    Then New information request record is created


  @stable
  Scenario: Operator user can update RFI with files
    When I signed in as "tasker" user
    And  I create new RFI with default values
    Then New information request record is created
    When I update record with files
    Then Information request record has attached files

  @stable
  Scenario: Operator can find RFI searching by Max Request Date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate |
         | 2015-01-24  |
    Then I can find RFI using specific search request
         |max_created_date|
         |2015-01-24      |

  @stable
  Scenario: Operator can find RFI searching by min Request Date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate |
         | 2015-01-24  |
    Then I can find RFI using specific search request
         |min_created_date|
         |2015-01-24      |

  @stable
  Scenario: Operator can find RFI searching by status
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | state       |
         | ASSIGNED    |
    Then I can find RFI using specific search request
         |state           |
         |ASSIGNED        |

  @stable
  Scenario: Operator can find RFI searching by min due date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate   | priority |
         | 2015-01-24    | 2        |
    Then I can find RFI using specific search request
         |min_due_date       |
         | 2015-01-25        |

  @stable
  Scenario: Operator can find RFI searching by max due date
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | createdDate   | priority |
         | 2015-01-24    | 2        |
    Then I can find RFI using specific search request
         |max_due_date       |
         | 2015-01-25        |


  @not_ready
  Scenario: Operator can find RFI searching by min last respond date
    When I signed in as "tasker" user
    And  I create new RFI with default values
    Then I can find RFI using todays max respond time


  @stable
  Scenario: Operator can find RFI searching by status
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | requestSource  |
         | FBI_test       |
    Then I can find RFI using specific search request
         |request_source  |
         |FBI_test        |

  @stable
  Scenario: Operator can find RFI searching by status
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | priority  |
         | 1       |
    Then I can find RFI using specific search request
         |min_priority  |
         |1             |

  @stable
  Scenario: Operator user can delete Draft request
    When I signed in as "tasker" user
    And  I create new RFI with specific values
         | state   |
         | DRAFT   |
    Then I can delete rfi

  @stable
  Scenario: Operator can cancel request
    When I signed in as "tasker" user
    And I create new RFI with default values
    And I cancel RFI
    Then RFI has status "Cancelled"


  @stable
  Scenario: Operator user can open details page of RFI
    When I signed in as "tasker" user
    And I create new RFI with default values
    Then I can get details of rfi


  @stable
  Scenario: Analyst user can assign RFI to himself
    When I signed in as "tasker" user
    And I create new RFI with default values
    And I signed in as "analyst" user
    Then I can take ownership of rfi


  @stable
  Scenario: Operator, Analyst, Report Admin, Admin cannot create RFI
    When I signed in as "user" user
    Then I cannot create new rfi with error code "403"
    When I signed in as "admin" user
    Then I cannot create new rfi with error code "403"
    When I signed in as "analyst" user
    Then I cannot create new rfi with error code "403"
    When I signed in as "approver" user
    Then I cannot create new rfi with error code "403"

