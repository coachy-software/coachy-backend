package life.coachy.backend.user.dto;


import life.coachy.backend.user.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserUpdateDtoTest {

  @Test
  public void toEntityTest() {
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .build();

    Assertions.assertEquals("User{identifier=null, "
        + "username='testUsername', "
        + "displayName='testDisplayName', "
        + "password='testPassword', "
        + "email='test@email.com', "
        + "avatar='http://www.com.com', "
        + "accountType=null, "
        + "roles=null}", String.valueOf(UserMapper.INSTANCE.userUpdateDtoToUser(dto)));
  }

}
