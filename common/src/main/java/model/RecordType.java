package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum RecordType {

    Voice("V", "Voice", "مكالمة هاتفية"),
    SMS("S", "SMS", "رسالة نصية"),
    Email("E", "Email", "بريد الالكتروني"),
    Metadata("M", "Metadata", "البيانات الوصفية"),
    Fax("F", "Fax", "فاكس"),
    InternetChat("C", "Internet Chat", "الدردشة عبر الإنترنت"),
    PhoneBook("PhoneBook", "PhoneBook", "دليل التلفونات" ),
    Subscriber("Subscriber", "Subscriber", "مكتتب"),
    VLR("VLR", "VLR", "VLR");

    private final String letterCode;
    private final String englishName;
    private final String arabicName;

    RecordType(String letterCode, String englishName, String arabicName) {
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

    private static final List<RecordType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();
    

    public static RecordType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
