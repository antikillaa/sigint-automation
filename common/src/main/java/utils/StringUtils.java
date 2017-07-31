package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.getLevenshteinDistance;

public class StringUtils {

    private static final Logger log = Logger.getLogger(StringUtils.class);
    private static Map<String, Integer> multiplier;
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        multiplier = new HashMap<>();
        multiplier.put("h", 3600);
        multiplier.put("m", 60);
        multiplier.put("s", 1);
    }

    public static int getDurationFromString(String text) {
        Integer sum = 0;
        for (String key : multiplier.keySet()) {
            Pattern pattern = Pattern.compile(String.format("[0-9]*%s", key));
            Matcher matcher = pattern.matcher(text);
            Boolean finded = matcher.find();
            if (!finded) {
                log.debug(String.format("Unable to parse %s from time string", key));
                continue;
            }
            String value = matcher.group().replace(String.format("%s", key), "");
            sum += Integer.parseInt(value) * multiplier.get(key);
        }
        return sum;
    }

    public static String stripQuotes(String s) {
        return s.replaceAll("^\"|\"$", "");
    }

    public static List<String> splitToList(String value) {
        // Remove whitespace and split by comma
        String[] strings = splitToArray(value);
        return Arrays.stream(strings)
                .filter(s -> !s.equals("[]"))
                .collect(Collectors.toList());
    }

    public static String[] splitToArray(String value) {
        // Remove whitespace and split by comma
        return value.split("\\s*,\\s*");
    }

    public static String[] trimSpaces(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static boolean hasRepeatedCharacters(String text, int sequenceLength) {
        String regex = String.format("([a-zA-Z0-9])\\1{%d}", sequenceLength - 1);
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(text).find();
    }

    public static boolean hasUpperAndLowerCharacters(String text) {
        boolean hasUppercase = !text.equals(text.toLowerCase());
        boolean hasLowercase = !text.equals(text.toUpperCase());

        return (hasLowercase && hasUppercase);
    }

    public static String prettyPrint(String jsonString) {
        try {
            Object json = mapper.readValue(jsonString, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            return jsonString;
        }
    }

    public static boolean stringEquals(String expected, String actual) {
        return expected.equalsIgnoreCase(actual);
    }

    public static boolean stringContainsAny(String inputStr, String... items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    public static boolean fuzzySearch(String str, String query) {

        if (query.contains("~")) {
            Integer tildaIndex = query.indexOf("~");
            String q = query.substring(0, tildaIndex);

            Integer maxDistance = 3;
            String suffix = query.substring(tildaIndex + 1);
            if (!suffix.isEmpty()) {
                maxDistance = Integer.valueOf(suffix);
            }

            String[] strings = str.split(" ");
            Set<Integer> distances = new HashSet<>();
            for (String s : strings) {
                Integer distance = getLevenshteinDistance(stripQuotes(s), q);
                distances.add(distance);
                if (distance <= maxDistance)
                    return true;
            }

            log.error("Pattern not founded, LevenshteinDistances:" + distances + " maxDistance:" + maxDistance);
        } else {
            log.error("Query string isn't fuzzy");
        }

        return false;
    }

    public static void saveStringToFile(String data, String filepath) {
        try {
            FileUtils.writeStringToFile(new File(filepath), data, "utf-8");
        } catch (IOException e) {
            log.error("Can't create file " + filepath + ":\n" + e.getMessage());
        }
    }
}
