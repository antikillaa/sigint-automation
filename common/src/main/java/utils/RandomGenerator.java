package utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.util.*;

public class RandomGenerator {


    public static LinkedHashSet<String> generatePhones(int maxPhones) {
        LinkedHashSet<String> phoneNumbers = new LinkedHashSet<>();
        int phones = RandomUtils.nextInt(maxPhones);
        for (int i=0; i<phones; i++){
            phoneNumbers.add(RandomStringUtils.randomNumeric(12));
        }

        return phoneNumbers;
    }

    public static LinkedHashSet<String> generateLanguages(int maxLanguages) {
        LinkedHashSet<String> languages = new LinkedHashSet<>();
        int numLanguages = RandomUtils.nextInt(maxLanguages);
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        int i = 0;
        while (i < numLanguages) {
            languages.add(countryCodes.get(RandomUtils.nextInt(countryCodes.size()-1)));
            i++;
        }
        return languages;
    }

    public static LinkedHashSet<String> generateRandomStrings(int maxNumber) {
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        int numMaxStrings = RandomUtils.nextInt(maxNumber);
        int i = 0;
        while (i < numMaxStrings) {
            strings.add(RandomStringUtils.randomAlphanumeric(5));
            i++;
        }
        return strings;
    }
}
