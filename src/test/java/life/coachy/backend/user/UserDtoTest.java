package life.coachy.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserDtoTest {

  @Test
  public void toEntityTest() {
    ObjectId id = ObjectId.get();
    UserDto dto = new UserDto(id, "testUsername", "test123", "passwordTest", "test@coachy.life",
        "http://coachy.life/test.png", AccountType.CHARGE, Sets.newTreeSet(Lists.newArrayList("USER", "ADMIN")));

    assertEquals("User{"
        + "identifier=" + id + ", "
        + "username='testUsername', "
        + "displayName='test123', "
        + "password='passwordTest', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/test.png', "
        + "accountType=CHARGE, "
        + "roles=[ADMIN, USER]}", UserMapper.INSTANCE.userDtoToUser(dto).toString());
  }

}
