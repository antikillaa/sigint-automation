package blocks.context.toolbars;

import pages.BaseSection;
import blocks.context.Context;

public abstract class TableToolbar extends BaseSection {
    private static final String toolBar = Context.baseSelector+ " div.pg-toolbar";

    public TableToolbar() {
        super(toolBar);
    }
}
