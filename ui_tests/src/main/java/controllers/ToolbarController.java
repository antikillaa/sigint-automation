package controllers;

import app_context.AppContext;
import pages.SigintPage;

public abstract class ToolbarController extends PageController {

    protected AppContext context = AppContext.getContext();

    public ToolbarController(SigintPage page) {
        super(page);
        initActions();
        initFilters();

    }
    protected abstract void initFilters();
    protected abstract void initActions();

}
