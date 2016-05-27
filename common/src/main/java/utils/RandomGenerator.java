package utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.util.*;

public class RandomGenerator {


    public static LinkedHashSet<String> generatePhones(int maxPhones) {

         class RandomPhone {
            String num1, num2, num3; //3 numbers in area code
            String set2, set3; //sequence 2 and 3 of the phone number

            Random generator = new Random();

            public String generate() {
                num1 = Integer.toString(generator.nextInt(7) + 1);
                num2 = Integer.toString(generator.nextInt(8));
                num3 = Integer.toString(generator.nextInt(8));


                set2 = Integer.toString(generator.nextInt(643) + 100);


                set3 = Integer.toString(generator.nextInt(8999) + 1000);

                return num1+num2+num3+set2+set3;
            }

        }
        LinkedHashSet<String> phoneNumbers = new LinkedHashSet<>();
        int phones = RandomUtils.nextInt(maxPhones);
        for (int i=0; i<phones; i++){
            phoneNumbers.add(new RandomPhone().generate());
        }

        return phoneNumbers;
    }

    public static LinkedHashSet<String> generateLanguagesCodes(int maxLanguages) {
        LinkedHashSet<String> languages = new LinkedHashSet<>();
        int numLanguages = RandomUtils.nextInt(maxLanguages);
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        int i = 0;
        while (i < numLanguages) {
            languages.add(countryCodes.get(RandomUtils.nextInt(countryCodes.size())));
            i++;
        }
        return languages;
    }

    public static String getRandomCountry() {
        List<String> locale = Arrays.asList(Locale.getISOCountries());
        String country = locale.get(RandomUtils.nextInt(locale.size()));
        Locale obj = new Locale("", country);
        return obj.getDisplayCountry();


    }

    public static String getRandomLanguage() {
        List<String> langCodes = Arrays.asList(Locale.getISOLanguages());
        String langCode = langCodes.get(RandomUtils.nextInt(langCodes.size()));
        Locale obj = new Locale("", langCode);
        return obj.getDisplayLanguage();
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

    public static <T>T getRandomItemFromList(List<T> list) {
        int index = RandomUtils.nextInt(list.size());
        return list.get(index);
    }
}
