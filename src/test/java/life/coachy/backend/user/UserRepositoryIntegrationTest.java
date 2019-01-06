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
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void storedUserShouldReturnProperValues() {
    ObjectId identifier = ObjectId.get();
    User user = new UserBuilder()
        .withUsername("(nnu*SCA8=4{v::^Z_bagna]Gz(CUN")
        .withPassword("test123")
        .withEmail("test@coachy.life")
        .withAvatar("http://www.coachy.life/some_avatar.jpg")
        .withIdentifier(identifier)
        .withAccountType(AccountType.CHARGE)
        .withRoles(Sets.newHashSet("ADMIN", "USER"))
        .build();

    this.mongoTemplate.insert(user);
    Optional<User> mongoUser = this.userRepository.findByUsername("(nnu*SCA8=4{v::^Z_bagna]Gz(CUN");

    this.mongoTemplate.remove(user);


    assertAll(
        () -> assertNotNull(mongoUser.get()),
        () -> assertEquals(mongoUser.get().toString(), user.toString())
    );
  }

}
