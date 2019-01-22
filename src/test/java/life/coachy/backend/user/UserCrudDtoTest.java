package life.coachy.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserCrudDtoTest {

  @Test
  public void toEntityTest() {
    UserCrudDto dto = new UserCrudDto("testUsername", "test123", "test@coachy.life", "http://coachy.life/test.png");

    assertEquals(dto.toEntity().toString(), "User{"
        + "identifier=null, "
        + "username='null', "
        + "displayName='testUsername', "
        + "password='test123', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/test.png', "
        + "accountType=null, "
        + "roles=null}");
  }

}
