Feature: Users can create, update, delete RFI records based on permissions
  Scenario: Operator user can create new RFI request
    When Send "create" request as "tasker" user
    Then New rfi record is created
