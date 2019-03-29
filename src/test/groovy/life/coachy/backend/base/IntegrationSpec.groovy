package life.coachy.backend.base

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import groovy.transform.TypeChecked
import life.coachy.backend.infrastructure.constants.Profiles
import org.bson.types.ObjectId
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@TypeChecked
@SpringBootTest
@ActiveProfiles([Profiles.TEST])
class IntegrationSpec extends Specification {

  @Autowired protected WebApplicationContext webApplicationContext
  @Autowired PasswordEncoder passwordEncoder
  @Autowired MongoTemplate mongoTemplate;

  MockMvc mockMvc

  @Before
  void setupMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build()
  }

  def setUpUser(ObjectId id, String username, String password, Set<String> permissions) {
    this.setUpUser(id, username, password, "", permissions)
  }

  def setUpUser(ObjectId id, String username, String password, String email, Set<String> permissions) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("username", username)
        this.put("password", IntegrationSpec.this.passwordEncoder.encode(password))
        this.put("email", email)
        this.put("roles", Sets.newHashSet("USER"))
        this.put("permissions", permissions)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(userDetails), "users")
  }


}
