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
public class UserMongoRepositoryIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserMongoRepository userMongoRepository;

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
    Optional<User> mongoUser = this.userMongoRepository.findByUsername("(nnu*SCA8=4{v::^Z_bagna]Gz(CUN");

    this.mongoTemplate.remove(user);


    assertAll(
        () -> assertNotNull(mongoUser.get()),
        () -> assertEquals(mongoUser.get().toString(), user.toString())
    );
  }

}
