Meta:
@skip

Lifecycle:
Before:
Given I sign in as admin user

Scenario: Targets migration
Meta:
@target_migration
When Produce targets <targetCount>, groups <groupsCount>
And Write targets to file

Examples:
| targetCount | groupsCount |
| 300 | 30 |