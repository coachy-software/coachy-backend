package life.coachy.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserUpdateDtoTest {

  @Test
  public void toEntityTest() {
    UserUpdateDto dto = new UserUpdateDto("testUsername", "test123", "test@coachy.life", "http://coachy.life/test.png");

    assertEquals("User{"
        + "identifier=null, "
        + "username='null', "
        + "displayName='testUsername', "
        + "password='test123', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/test.png', "
        + "accountType=null, "
        + "roles=null, "
        + "schedules=null}", dto.toEntity().toString());
  }

}
