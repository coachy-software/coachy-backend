package life.coachy.backend.infrastructure.constants;

public final class ApiLayers {

  private ApiLayers() {}

  private static final String API_ROOT = "api/";
  public static final String USERS = API_ROOT + "users";
  public static final String ERROR = "/error";
  public static final String EXERCISE_TEMPLATES = API_ROOT + "exercises";
  public static final String SCHEDULES = API_ROOT + "schedules";
  public static final String BOARDS = API_ROOT + "boards";

}
