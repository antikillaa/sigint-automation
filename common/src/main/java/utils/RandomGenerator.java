package utils;

import model.AppContext;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RandomGenerator {

    private static Logger log = Logger.getLogger(RandomGenerator.class);

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
        int i = 0;
        do {
            phoneNumbers.add(new RandomPhone().generate());
            i++;
        } while (i < phones);

        return phoneNumbers;
    }

    public static LinkedHashSet<String> generateLanguagesCodes(int maxLanguages) {
        LinkedHashSet<String> languages = new LinkedHashSet<>();
        int numLanguages = RandomUtils.nextInt(maxLanguages);
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        int i = 0;
        do {
            languages.add(countryCodes.get(RandomUtils.nextInt(countryCodes.size())));
            i++;
        } while (i < numLanguages);
        return languages;
    }

    public static String getRandomCountry() {
        List<String> countries = new LinkedList<>(AppContext.getContext().getCountries().values());
        String country = countries.get(RandomUtils.nextInt(countries.size()-1));
        return country;
    }
    

    public static String getRandomLanguage() {
        List<String> languages = new LinkedList<>(AppContext.getContext().getLanguages().values());
        String language = languages.get(RandomUtils.nextInt(languages.size()-1));
        return language;
    }

    public static LinkedHashSet<String> generateRandomStrings(int maxNumber) {
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        int numMaxStrings = RandomUtils.nextInt(maxNumber);
        int i = 0;
        do {
            strings.add(RandomStringUtils.randomAlphanumeric(5));
            i++;
        } while (i < numMaxStrings);
        return strings;
    }

    public static <T>T getRandomItemFromList(List<T> list) {
        int index = RandomUtils.nextInt(list.size());
        return list.get(index);
    }

    public static String todayDateInterval() {

        Date currDate = new Date();
        Date startTime = getStartDate(currDate);
        Date endTime =  getEndDate(currDate);
        DateFormat format = new SimpleDateFormat("dd.MM.YYYY HH:MM:ss");
        return format.format(startTime) + " - " + format.format(endTime);
    }

    public static int getRandomDuration() {
        return (RandomUtils.nextInt(120)+1)*60;
    }

    private static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE,01);
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTime();
    }

    private static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    public static String generateCountryCode() {
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        return countryCodes.get(RandomUtils.nextInt(countryCodes.size()));
    }

    public static String getCountryName(String countryCode) {
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }
    
    public static String getCountryCode(String countryName) {
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        try {
            return countries.get(countryName);
        } catch (NullPointerException e) {
            throw new AssertionError("There is no country with name " + countryName);
        }
    }

    public static LinkedHashSet<String> generateKeywords(int maxNumber) {
        String text = FileHelper.readTxtFile("keywords.txt");
        ArrayList<String> strings = new ArrayList<>();
        if (text != null) {
            String[] values = text.split(" ");
            for (String value : values) {
                value = value.trim().replace(",", "").replace(".", "");
                if (value.length() > 2) {
                    strings.add(value);
                }
            }
        }

        LinkedHashSet<String> keys = new LinkedHashSet<>();
        int numMaxStrings = RandomUtils.nextInt(maxNumber);
        int i = 0;
        do {
            if (text != null) {
                int index = RandomUtils.nextInt(strings.size());
                keys.add(strings.get(index));
            }
            i++;
        } while (i < numMaxStrings);
        return keys;
    }

}

