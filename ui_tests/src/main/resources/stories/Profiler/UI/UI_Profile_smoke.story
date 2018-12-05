Meta:


Lifecycle:
Before:
Scope: SCENARIO
Given load story ../../aux-main-stories/auxSignIn.story with example table:
data/QE_login_analyst.table


Scenario: Create and delete a profile smoke test
Meta: @devsmoke



Given I navigate to Search

When I enter search criteria (<EIDEntityUIDNumber>) on the Search page
When I start search on the Search page
Given I setup Search Authorization

When I open Card View
When I start search on the Search page
When I select all EID entity cards having identifiers: |<EIDEntityVISANumber>|
When I create new target for selected items
Given I set Target's Name to <TargetName>
Given I set Target's Classification to <ProfilerEntityClas>
Given I set Target's Organization Units to |<ProfilerEntityOU>|
Given I set Target's Category to <TargetCategory>
Given I set Target's Assignment Priority to <ProfilerEntityAP>
Given I set Target's Status to <ProfilerEntityTS>
Given I set Target's Active Until to <ProfilerEntityAU>
Given I set Target's Threat Score Impact to <ProfilerEntityTSI>
Given I set Target's File to |<FileName>|

Given I create Target

Given I navigate to Search

When I enter search criteria (<TargetName>) on the Search page
When I start search on the Search page
Given I setup Search Authorization
Given I view 1-st displayed Profiler entity in Profiler
Then I should see Profiler page
Then I should see Profile Details tab on the Profiled page
Then I should see Communication tab on the Profiled page
Then I should see Travel and Movement tab on the Profiled page
Then I should see Mentions tab on the Profiled page
Then I should see Network tab on the Profiled page
Then I should see Attachments tab on the Profiled page
Then I should see Target Activity tab on the Profiled page
Then I should see Insights tab on the Profiled page


Given I navigate to CB Finder
When I select file with Name (<FileName>) in the CB Finder
Then I check if target by name (<TargetName>) is present under case/file which is currently selected in the CB Finder
When I open target by name (<TargetName>) is present under case/file which is currently selected in the CB Finder
Given load story ../../aux-after-stories/auxDeleteTarget.story with example table:
data/BasicProfile.data

Examples:
|FileName   |TargetName				|ProfilerEntityClas|ProfilerEntityOU|TargetCategory|ProfilerEntityAP|ProfilerEntityTS|ProfilerEntityAU                              |ProfilerEntityTSI|ReportClassifInit|ReportCreatedForInit|ReportOrgUnitInit|ReportSubject   |ReportDescrInit  |ReportConsidInit |ReportRecommInit |ReportNotesInit   |EIDEntityUIDNumber|EIDEntityVISANumber|
|Default	|Target for profile test|TOP SECRET-CIO    |QE_UIAuto_Team  |POI              |High            |Active          |{current_day}+1/{current_month}/{current_year}|Medium 30 |TOP SECRET|UI Test Team  |QE_UIAuto_Team |For test purpose|Testing description|Consideration for test|Testing recommendations|Initial test notes|3642367978578790690|ad3kNtL6cIfSVUQn58g7|
