package life.coachy.backend.util;

public final class StringUtil {

  private StringUtil() {

  }

  public static String lowerCaseAt(int index, String source) {
    return caseSensitive(false, index, source);
  }

  public static String upperCaseAt(int index, String source) {
    return caseSensitive(true, index, source);
  }

  private static String caseSensitive(boolean upperCase, int index, String source) {
    String firstChar = String.valueOf(source.charAt(index));
    String casedFirstChar = upperCase ? firstChar.toUpperCase() : firstChar.toLowerCase();
    return casedFirstChar + source.substring(1);
  }


}
