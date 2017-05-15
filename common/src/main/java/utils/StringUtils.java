package utils;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {

    private static Logger log = Logger.getLogger(StringUtils.class);
    private static Map<String, Integer> multiplier;

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
        return s.replaceAll("'", "").replaceAll("\"", "").trim();
    }

    public static List<String> toList(String value) {
        String[] strings = value.split(",");
        return Arrays.stream(strings)
                .filter(s -> !s.equals("[]"))
                .collect(Collectors.toList());
    }
}
