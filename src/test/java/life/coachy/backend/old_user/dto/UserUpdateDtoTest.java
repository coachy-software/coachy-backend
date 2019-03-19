package life.coachy.backend.old_user.dto;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserUpdateDtoTest {

  private UserUpdateDto dto;

  @Before
  public void setUp() throws Exception {
    this.dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .build();
  }

  @Test
  public void toEntityTest() {
    Assertions.assertEquals("User{identifier=null, "
        + "username='testUsername', "
        + "displayName='testDisplayName', "
        + "password='testPassword', "
        + "email='test@email.com', "
        + "avatar='http://www.com.com', "
        + "accountType=null, "
        + "roles=null, "
        + "permissions=null, "
        + "boardIdentifier=null}", String.valueOf(UserMapper.INSTANCE.userUpdateDtoToUser(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getUsername()),
        () -> assertNotNull(this.dto.getDisplayName()),
        () -> assertNotNull(this.dto.getPassword()),
        () -> assertNotNull(this.dto.getEmail()),
        () -> assertNotNull(this.dto.getAvatar())
    );
  }


}
