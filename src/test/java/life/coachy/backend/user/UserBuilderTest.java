package life.coachy.backend.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserBuilderTest {

  private final static ObjectId IDENTIFIER = ObjectId.get();
  private final static User USER = UserBuilder.createBuilder()
      .withIdentifier(IDENTIFIER)
      .withUsername("(kybb3@Q[fxX;fX7EW5%/^FJJbR#EW")
      .withPassword("test")
      .withAvatar("http://coachy.life/some_avatar.jpg")
      .withEmail("test@coachy.life")
      .withRoles(Sets.newTreeSet(Lists.newArrayList("ADMIN", "USER")))
      .withAccountType(AccountType.COACH)
      .build();

  @Test
  public void userValuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(USER),
        () -> assertNotNull(USER.getUsername()),
        () -> assertNotNull(USER.getPassword()),
        () -> assertNotNull(USER.getAvatar()),
        () -> assertNotNull(USER.getEmail()),
        () -> assertNotNull(USER.getRoles()),
        () -> assertNotNull(USER.getAccountType()),
        () -> assertNotNull(USER.getIdentifier())
    );
  }

  @Test
  public void toStringTest() {
    String userString = "User{identifier=" + IDENTIFIER.toHexString() + ", "
        + "username='(kybb3@Q[fxX;fX7EW5%/^FJJbR#EW', "
        + "displayName='null', "
        + "password='test', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/some_avatar.jpg', "
        + "accountType=COACH, "
        + "roles=[ADMIN, USER], "
        + "permissions=null, "
        + "boardIdentifier=null}";

    assertNotNull(USER.toString());
    assertEquals(userString, USER.toString());
  }

}
