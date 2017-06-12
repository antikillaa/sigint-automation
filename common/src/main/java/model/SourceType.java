package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum SourceType{

    Tactical("T", "Terra", "Terra"),
    Strategic("S", "Speed", "Speed"),
    F("F", "Falcon", "Falcon"),
    C("C", "CIO", "CIO"),
    O("O", "Octopus", "Octopus"),
    J("J", "Jernas", "Jernas"),
    HumanInput("H", "HumInt", "HumInt"),
    Etisalat("E", "External", "External"),
    X("X", "Target Team", "Target Team"),

    MANUAL("MANUAL", "MANUAL", "MANUAL"),
    T("T", "T", "T"),
    S("S", "S", "S"),
    TWEET("TWEET", "TWEET", "TWEET"),
    SY("SY", "SY", "SY");

    private final String letterCode;
    private final String englishName;
    private final String arabicName;

    SourceType(String letterCode, String englishName, String arabicName) {
        this.letterCode = letterCode;
        this.englishName = englishName;
        this.arabicName = arabicName;
    }

    public String toLetterCode() {
        return letterCode;
    }

    public String toEnglishName() {
        return englishName;
    }

    public String toArabicName() {
        return arabicName;
    }

    private static final List<SourceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();
    
    public static SourceType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}