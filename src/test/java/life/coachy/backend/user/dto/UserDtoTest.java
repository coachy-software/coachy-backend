package life.coachy.backend.user.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import life.coachy.backend.user.UserMapper;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserDtoTest {

  @Test
  public void toEntityTest() {
    ObjectId id = ObjectId.get();
    UserDto dto = UserDtoBuilder.createBuilder()
        .withIdentifier(id)
        .withUsername("testUsername")
        .withDisplayName("test123")
        .withPassword("passwordTest")
        .withEmail("test@coachy.life")
        .withAvatar("http://coachy.life/test.png")
        .withAccountType("CHARGE")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("ADMIN", "USER")))
        .build();

    Assertions.assertEquals("User{"
        + "identifier=" + id + ", "
        + "username='testUsername', "
        + "displayName='test123', "
        + "password='passwordTest', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/test.png', "
        + "accountType=CHARGE, "
        + "roles=[ADMIN, USER]}", String.valueOf(UserMapper.INSTANCE.userDtoToUser(dto)));
  }

}
