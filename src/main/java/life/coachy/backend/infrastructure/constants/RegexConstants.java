package life.coachy.backend.infrastructure.constants;

public final class RegexConstants {

  public static final String REGEX_NO_SPACE_AND_SPECIAL_CHARS = "^[a-zA-Z0-9]*$";
  public static final String REGEX_NO_SPACE = "^\\S{0,}$";

  private RegexConstants() {}

}
