package life.coachy.backend.user.dto;

import life.coachy.backend.user.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserRegistrationDtoTest {

  @Test
  public void toEntityTest() {

    UserRegistrationDto dto = UserRegistrationDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withPassword("testPassword")
        .withMatchingPassword("testPassword")
        .withEmail("test@coachy.life")
        .withAccountType("COACH")
        .build();

    Assertions.assertEquals("User{"
        + "identifier=null, "
        + "username='testUsername', "
        + "displayName='null', "
        + "password='testPassword', "
        + "email='test@coachy.life', "
        + "avatar='null', "
        + "accountType=COACH, "
        + "roles=null}", String.valueOf(UserMapper.INSTANCE.userRegistrationDtoToUser(dto)));
  }

}
