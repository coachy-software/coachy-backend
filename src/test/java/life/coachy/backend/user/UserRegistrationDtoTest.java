package life.coachy.backend.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserRegistrationDtoTest {

  @Test
  public void toEntityTest() {
    UserRegistrationDto dto = new UserRegistrationDto("testUsername", "testPassword", "testPassword",
        "test@coachy.life", "test@coachy.life", "COACH");

    assertEquals(dto.toEntity().toString(), "User{"
        + "identifier=null, "
        + "username='testUsername', "
        + "displayName='null', "
        + "password='testPassword', "
        + "email='test@coachy.life', "
        + "avatar='null', "
        + "accountType=COACH, "
        + "roles=null, "
        + "schedules=null}");
  }

}
