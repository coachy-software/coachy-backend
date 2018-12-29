package life.coachy.backend.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserRegistrationDtoTest {

  @Test
  public void toJsonTest() {
    UserRegistrationDto dto = new UserRegistrationDto("testUsername", "testPassword", "testPassword",
        "test@coachy.life", "test@coachy.life", "COACH");

    assertEquals(dto.toJson(), "{\r\n"
        + "  \"username\" : \"testUsername\",\r\n"
        + "  \"password\" : \"testPassword\",\r\n"
        + "  \"matchingPassword\" : \"testPassword\",\r\n"
        + "  \"email\" : \"test@coachy.life\",\r\n"
        + "  \"matchingEmail\" : \"test@coachy.life\",\r\n"
        + "  \"accountType\" : \"COACH\"\r\n"
        + "}");
  }

  @Test
  public void toEntityTest() {
    UserRegistrationDto dto = new UserRegistrationDto("testUsername", "testPassword", "testPassword",
        "test@coachy.life", "test@coachy.life", "COACH");

    assertEquals(dto.toEntity().toString(), "User{"
        + "identifier=null, "
        + "username='testUsername', "
        + "password='testPassword', "
        + "email='test@coachy.life', "
        + "avatar='null', "
        + "accountType=COACH, "
        + "roles=null}");
  }

}
