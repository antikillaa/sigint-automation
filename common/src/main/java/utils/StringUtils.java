package utils;

/**
 * Created by ayak on 3/22/17.
 */
public class StringUtils {

  public static String stripQuotes(String s) {
    return s.replaceAll("'", "").replaceAll("\"", "").trim();
  }
}
