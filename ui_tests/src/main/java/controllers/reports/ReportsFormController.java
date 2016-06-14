package controllers.reports;

import controllers.PageController;
import model.Report;
import model.User;
import pages.SigintPage;
import pages.reports.ReportsPage;

public class ReportsFormController<T extends ReportsPage> extends PageController<T> {

    public ReportsFormController(SigintPage page, Class<T> userClass) {
        super(page, userClass);
    }

    public Report fillForm(Report report) {
        getPage().setSubject(report.getSubject());
        return report;
    }

    public void saveAsDraft() {
        getPage().getActionBar().clickSaveDraft();
    }

    public void submitReport() {getPage().getActionBar().clickSubmitButton();}


    public void takeOwnership(Report report, User user) {
        getPage().getActionBar().clickTakeOwnershipButton();
        report.setOwner(user.getName());}


    public void removeOwnership(Report report) {
        getPage().getActionBar().clickRemoveOwnership();
        report.setOwner("");}
}
