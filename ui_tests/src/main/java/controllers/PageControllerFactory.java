package controllers;

import pages.SigintPage;

public interface PageControllerFactory{

    SigintPage getPage();

    TableController getTableController();

    ToolbarController getToolbarController();

    PageController getSearchController();

}


