package life.coachy.backend.user.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import life.coachy.backend.user.UserMapper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserDtoTest {

  private ObjectId id;
  private UserDto dto;

  @Before
  public void setUp() throws Exception {
    this.id = ObjectId.get();
    this.dto = UserDtoBuilder.createBuilder()
        .withIdentifier(this.id)
        .withUsername("testUsername")
        .withDisplayName("test123")
        .withPassword("passwordTest")
        .withEmail("test@coachy.life")
        .withAvatar("http://coachy.life/test.png")
        .withAccountType("CHARGE")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("ADMIN", "USER")))
        .build();
  }

  @Test
  public void toEntityTest() {
    assertEquals("User{"
        + "identifier=" + this.id + ", "
        + "username='testUsername', "
        + "displayName='test123', "
        + "password='passwordTest', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/test.png', "
        + "accountType=CHARGE, "
        + "roles=[ADMIN, USER]}", String.valueOf(UserMapper.INSTANCE.userDtoToUser(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getIdentifier()),
        () -> assertNotNull(this.dto.getUsername()),
        () -> assertNotNull(this.dto.getDisplayName()),
        () -> assertNotNull(this.dto.getPassword()),
        () -> assertNotNull(this.dto.getEmail()),
        () -> assertNotNull(this.dto.getAvatar()),
        () -> assertNotNull(this.dto.getAccountType()),
        () -> assertNotNull(this.dto.getRoles())
    );
  }

}
