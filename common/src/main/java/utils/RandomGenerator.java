package utils;

import app_context.AppContext;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RandomGenerator {

    private static Logger log = Logger.getLogger(RandomGenerator.class);
    private static AppContext context = AppContext.get();
    
    
    public static int generateRandomInteger(int minNumber, int maxNumber) {
        Random random = new Random();
        return random.nextInt((maxNumber-minNumber)+1) + minNumber;
    }

    public static String generatePhone() {

        class RandomPhone {
            private String num, num1, num2, num3; //3-4 numbers in area code
            private String set1, set2, set3; //sequence 2 and 3 of the phone number

            private Random generator = new Random();

            public String generate() {
                num = generator.nextInt(1) == 1 ? "1" : "";
                num1 = Integer.toString(generator.nextInt(7) + 1);
                num2 = Integer.toString(generator.nextInt(8));
                num3 = Integer.toString(generator.nextInt(8));

                set1 = Integer.toString(generator.nextInt(9999)); //city code

                set2 = Integer.toString(generator.nextInt(643) + 100);
                set3 = Integer.toString(generator.nextInt(8999) + 1000);

                return num + num1 + num2 + num3 + set1 + set2 + set3;
            }
        }

        return new RandomPhone().generate();
    }

    public static String getRandomLanguageCode() {
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        return countryCodes.get(RandomUtils.nextInt(countryCodes.size()));

    }

    public static String getRandomCountry() {
        List<String> countries = new LinkedList<>(context.getCountries().values());
        String country = countries.get(RandomUtils.nextInt(countries.size() - 1));
        return country;
    }

    public static String getRandomLanguage() {
        List<String> languages = new LinkedList<>(context.getLanguages().values());
        String language = languages.get(RandomUtils.nextInt(languages.size() - 1));
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

    public static <T> T getRandomItemFromList(List<T> list) {
        int index = RandomUtils.nextInt(list.size());
        return list.get(index);
    }
    

    public static String todayDateInterval() {

        Date currDate = new Date();
        Date startTime = getStartDate(currDate);
        Date endTime = getEndDate(currDate);
        DateFormat format = new SimpleDateFormat("dd.MM.YYYY HH:MM:ss");
        return format.format(startTime) + " - " + format.format(endTime);
    }

    public static int getRandomDuration() {
        return (RandomUtils.nextInt(120) + 1) * 60;
    }

    private static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 01);
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
        if (countryCode == null) {
            return null;
        }
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }

    public static String getCountryCode(String countryName) {
        if (countryName == null) {
            return null;
        }
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
        LinkedHashSet<String> keys = new LinkedHashSet<>();
        int maxKeywords = RandomUtils.nextInt(maxNumber + 1);

        int i = 0;
        do {
            keys.add(RandomStringUtils.randomAlphabetic(6));
            i++;
        } while (i < maxKeywords);

        return keys;
    }

    public static String generateIMSI() {
        return RandomStringUtils.randomNumeric(10);
    }

    public static String generateSMSText(boolean fromFile) {
        if (fromFile) {
            String text = FileHelper.readTxtFile("sms_text.txt");

            if (text != null) {
                int sms_length = new Random().nextInt(160);
                int beginIndex = new Random().nextInt(text.length() - sms_length);
                return text.substring(beginIndex, beginIndex + sms_length);
            } else {
                return "";
            }
        } else {
            return RandomStringUtils.randomAlphanumeric(12);
        }
    }

    /**
     * Generate random string with 'mention' substring
     *
     * @param mention substring
     * @return random string with 'mention' substring
     */
    public static String generateSMSText(String mention) {
        Random random = new Random();
        String prefix = RandomStringUtils.randomAlphanumeric(random.nextInt(15));
        String suffix = random.nextBoolean() ? " " : "" + RandomStringUtils.randomAlphanumeric(random.nextInt(15));
        return prefix +
                (random.nextBoolean() ? " " : "") +
                mention +
                (random.nextBoolean() ? " " : "") +
                suffix.trim();
    }

    public static String generateEmail(){
        int size = generateRandomInteger(3,15);
        String username = RandomStringUtils.randomAlphanumeric(size);
        return username+"@gmail.com";

    }



}

