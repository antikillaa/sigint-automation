package model;

public enum SourceType {

    Tactical("T", "Terra", "Terra"),
    Strategic("S", "Speed", "Speed"),
    F("F", "Falcon", "Falcon"),
    C("C", "CIO", "CIO"),
    O("O", "Octopus", "Octopus"),
    J("J", "Jernas", "Jernas"),
    HumanInput("H", "HumInt", "HumInt"),
    Etisalat("E", "External", "External");

    private final String letterCode;
    private final String englishName;
    private final String arabicName;

    private SourceType(String letterCode, String englishName, String arabicName) {
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
}