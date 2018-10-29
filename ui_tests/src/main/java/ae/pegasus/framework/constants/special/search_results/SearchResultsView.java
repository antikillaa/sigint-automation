package ae.pegasus.framework.constants.special.search_results;

public enum SearchResultsView {
    CARD_VIEW("Card View"),
    GRID_VIEW("Grid View"),
    MAP_VIEW("Map View");

    private final String displayName;

    SearchResultsView (String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static SearchResultsView getEnumByName(String viewName) {
        for (SearchResultsView item : SearchResultsView.values()) {
            if (item.getDisplayName().equalsIgnoreCase(viewName)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Search Results View with name '" + viewName + "' was not found");
    }
}
