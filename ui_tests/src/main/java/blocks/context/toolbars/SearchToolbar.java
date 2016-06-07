package blocks.context.toolbars;

import blocks.context.Context;
import pages.BaseSection;

public abstract class SearchToolbar extends BaseSection {
    private static final String toolBar = Context.baseSelector+ " search-form";
    public SearchToolbar() {
        super(toolBar);
    }
}
