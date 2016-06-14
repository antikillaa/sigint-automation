package blocks.navigation.menus;

import blocks.navigation.MainMenu;
import controllers.PageControllerFactory;
import controllers.reports.all_page.ReportsAllController;
import controllers.reports.draft_page.ReportsDraftController;
import controllers.reports.ready_for_review.ReportsReadyController;

public class ReportsMenu extends MainMenu {

    public ReportsMenu(String menuName) {
        super(menuName);
    }

    @Override
    protected PageControllerFactory getController(String name) {
        if (name.equalsIgnoreCase("draft")) {
            return new ReportsDraftController();
        } else if (name.equalsIgnoreCase("all")) {
            return new ReportsAllController();
        } else  if (name.equalsIgnoreCase("ready")) {
            return new ReportsReadyController();
        }

        throw new AssertionError("There is no menu Reports -> "+name);
    }


}
