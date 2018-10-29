package ae.pegasus.framework.steps.special;

import org.jbehave.core.annotations.When;
import ae.pegasus.framework.pages.Pages;

public class UIActionsWithSelectedSteps {
    @When("I create new report for selected items")
    public void iCreateNewReport() {
        Pages.actionsWithSelected().addSelectedToReport();
        Pages.attachToReportPage().createNewReport();
    }


    @When("I add selected items to the existing report ($reportName)")
    public void iAddSelectedToExistingReport(String reportName) {
        Pages.actionsWithSelected().addSelectedToReport();
        Pages.attachToReportPage().addToExistingReport(reportName);
    }


    @When("I create new target for selected items")
    public void iCreateNewTarget() {
        Pages.actionsWithSelected().addSelectedToTarget();
        Pages.attachToTargetPage().waitDialogLoading();
        Pages.attachToTargetPage().createNewTarget();
    }

    @When("I add selected items to the existing target ($targetName)")
    public void iAddSelectedToExistingTarget(String targetName) {
        Pages.actionsWithSelected().addSelectedToTarget();
        Pages.attachToTargetPage().waitDialogLoading();
        Pages.attachToTargetPage().addToExistingTarget(targetName);
    }

    @When("I take ownership on selected items")
    public void iAssignSelectedToMe() {
        Pages.actionsWithSelected().assignSelectedToMe();
    }

    @When("I assign selected items to ($userName)")
    public void iAssignSelectedOther(String userName) {
        Pages.actionsWithSelected().assignSelectedToOther();
        Pages.assignRecordsPage().waitDialogLoading();
        Pages.assignRecordsPage().assignRecordToUser(userName);
    }

    @When("I unassign selected items")
    public void iUnassignSelected() {
        Pages.actionsWithSelected().unassignSelected();
    }
}
