package controllers;

import blocks.context.toolbars.TableToolbar;
import model.AppContext;
import pages.SigintPage;

public abstract class TableToolbarController<F extends TableToolbar, A extends TableToolbar>
        extends PageController {

    protected F filterPanel;
    protected A actionPanel;
    protected AppContext context = AppContext.getContext();

    public TableToolbarController(SigintPage page, Class<F> filterClass, Class<A> actionClass ) {
        super(page);
        initActionsPanel(actionClass);
        initPanel(filterClass);
    }

    private void initPanel(Class<F> filterClass) {
        filterPanel = filterClass.cast(this.getPage().context().getToolbar().getFilters());

    }

    private void initActionsPanel(Class<A> actionClass) {
        actionPanel = actionClass.cast(this.getPage().context().getToolbar().getActions());
    }

}
