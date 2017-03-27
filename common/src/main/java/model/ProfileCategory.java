package model;

public enum ProfileCategory {

    Dangerous("Dangerous"),
    POI("Person of Interest");

    private final String displayName;

    ProfileCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
