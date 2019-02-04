package life.coachy.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserUpdateDtoTest {

  @Test
  public void toEntityTest() {
    UserUpdateDto dto = new UserUpdateDto("username", "123123123", "123123123", "test@email.com", "http://www.com.com");

    assertEquals("User{identifier=null, "
        + "username='username', "
        + "displayName='123123123', "
        + "password='123123123', "
        + "email='test@email.com', "
        + "avatar='http://www.com.com', "
        + "accountType=null, "
        + "roles=null}", UserMapper.INSTANCE.userUpdateDtoToUser(dto).toString());
  }

}
