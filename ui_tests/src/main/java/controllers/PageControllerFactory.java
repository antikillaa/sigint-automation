package controllers;

public interface PageControllerFactory<T extends TableController,
        TB extends TableToolbarController,S extends PageController> {

    T getTableController();
    TB getToolbarController();
    S getSearchController();



}


