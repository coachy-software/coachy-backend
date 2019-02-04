package life.coachy.backend.user.dto;

import life.coachy.backend.user.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserRegistrationDtoTest {

  private UserRegistrationDto dto;

  @Before
  public void setUp() throws Exception {
    this.dto = UserRegistrationDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withPassword("testPassword")
        .withMatchingPassword("testPassword")
        .withEmail("test@coachy.life")
        .withAccountType("COACH")
        .build();
  }

  @Test
  public void toEntityTest() {
    assertEquals("User{"
        + "identifier=null, "
        + "username='testUsername', "
        + "displayName='null', "
        + "password='testPassword', "
        + "email='test@coachy.life', "
        + "avatar='null', "
        + "accountType=COACH, "
        + "roles=null}", String.valueOf(UserMapper.INSTANCE.userRegistrationDtoToUser(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getUsername()),
        () -> assertNotNull(this.dto.getPassword()),
        () -> assertNotNull(this.dto.getMatchingPassword()),
        () -> assertNotNull(this.dto.getEmail()),
        () -> assertNotNull(this.dto.getAccountType())
    );
  }

}
