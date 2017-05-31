package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Classification {

    OUO("OUO", null, "OFFICIAL USE ONLY"),
    C("C", "OUO", "CONFIDENTIAL"),
    S("S", "C", "SECRET"),
    TS("TS", "S", "TOP SECRET"),
    TSCIO("TS-CIO", "TS", "TOP SECRET-CIO"),
    TSOS("TS-OS", "TS", "TOP SECRET-OS"),
    TSSCI("TS-SCI", "TS", "TOP SECRET-SCI");

    private final String name;
    private final String parentName;
    private final String displayName;
    private final String description = null;

    Classification(String name, String parentName, String displayName) {
        this.name = name;
        this.parentName = parentName;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    private static final List<Classification> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    public static Classification random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
