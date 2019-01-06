/*
 * MIT License
 *
 * Copyright (c) 2018 Coachy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package life.coachy.backend.user;

import com.google.common.collect.Sets;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserBuilderTest {

  private final static ObjectId IDENTIFIER = ObjectId.get();
  private final static User USER = new UserBuilder()
      .withIdentifier(IDENTIFIER)
      .withUsername("(kybb3@Q[fxX;fX7EW5%/^FJJbR#EW")
      .withPassword("test")
      .withAvatar("http://coachy.life/some_avatar.jpg")
      .withEmail("test@coachy.life")
      .withRoles(Sets.newHashSet("USER, ADMIN"))
      .withAccountType(AccountType.COACH)
      .build();

  @Test
  public void shouldReturnNotNullObject() {
    assertNotNull(USER);
  }

  @Test
  public void userValuesShouldNotBeNull() {
    assertAll(
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
        + "password='test', "
        + "email='test@coachy.life', "
        + "avatar='http://coachy.life/some_avatar.jpg', "
        + "accountType=COACH, "
        + "roles=[USER, ADMIN]}";

    assertNotNull(USER.toString());
    assertEquals(userString, USER.toString());
  }

}
