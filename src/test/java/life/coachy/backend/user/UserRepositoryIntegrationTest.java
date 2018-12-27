package life.coachy.backend.user;

import com.google.common.collect.Sets;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
    Assertions.assertEquals(mongoUser.get().toString(), user.toString());
  }

  @Test
  public void storeUserShouldNotReturnProperValues() {
    ObjectId identifier = ObjectId.get();
    User user = new UserBuilder()
        .withUsername("Q^&(gc<M+Zp52k`C,.x4kb5f)hTPK^")
        .withPassword("test123")
        .withEmail("test@coachy.life")
        .withAvatar("http://www.coachy.life/some_avatar.jpg")
        .withIdentifier(identifier)
        .withAccountType(AccountType.CHARGE)
        .withRoles(Sets.newHashSet("ADMIN", "USER"))
        .build();

    this.mongoTemplate.insert(user);
    Optional<User> mongoUser = this.userRepository.findByUsername("Q^&(gc<M+Zp52k`C,.x4kb5f)hTPK^");

    this.mongoTemplate.remove(user);
    Assertions.assertThrows(AssertionError.class,
        () -> Assert.assertNotEquals(mongoUser.get().toString(), user.toString())
    );
  }

}
