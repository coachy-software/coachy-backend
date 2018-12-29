package life.coachy.backend.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserAuthenticationDtoTest {

  @Test
  public void toEntityTest() {
    UserAuthenticationDto dto = new UserAuthenticationDto("testUsername", "testPassword");

    assertEquals(dto.toEntity().toString(), "User{"
        + "identifier=null, "
        + "username='testUsername', "
        + "password='testPassword', "
        + "email='null', "
        + "avatar='null', "
        + "accountType=null, "
        + "roles=null}");
  }

}
