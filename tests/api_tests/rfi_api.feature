
Feature: Users can create, update, delete RFI records based on permissions


  Scenario: Operator user can create new RFI request
    When Send create request as "tasker" user
    Then New rfi record is created

  Scenario: Operator user can update RFI with files
    When Send create request as "tasker" user
    And Send update request with files as "tasker" user
    Then rfi record has attached files
