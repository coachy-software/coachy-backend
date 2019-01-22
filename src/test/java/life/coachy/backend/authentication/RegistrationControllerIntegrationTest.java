package life.coachy.backend.authentication;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.user.UserRegistrationDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrationControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private RegistrationController registrationController;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;
  private BasicDBObject user;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.registrationController).build();
  }

  @Test
  public void registrationShouldReturn409WhenUserAlreadyExists() throws Exception {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("username", "test918238901802301");
      this.put("password", RegistrationControllerIntegrationTest.this.passwordEncoder.encode("test"));
      this.put("roles", Sets.newHashSet("USER", "ADMIN"));
    }};

    this.user = new BasicDBObject(userDetails);
    this.mongoTemplate.insert(this.user, "users");

    UserRegistrationDto dto = new UserRegistrationDto("test918238901802301", "test123",
        "test123", "test@coachy.life", "test@coachy.life", "COACH");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @After
  public void tearDown() throws Exception {
    if (this.user != null) {
      this.mongoTemplate.remove(this.user, "users");
    }
  }

}
