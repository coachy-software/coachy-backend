package life.coachy.backend.authentication;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.user.dto.UserRegistrationDto;
import life.coachy.backend.user.dto.UserRegistrationDtoBuilder;
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

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.registrationController).build();
  }

  @Test
  public void registrationShouldReturn409WhenUserAlreadyExists() throws Exception {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("username", "testUsername");
      this.put("password", RegistrationControllerIntegrationTest.this.passwordEncoder.encode("test123"));
      this.put("roles", Sets.newHashSet("USER"));
    }};

    BasicDBObject user = new BasicDBObject(userDetails);
    this.mongoTemplate.insert(user, "users");

    UserRegistrationDto dto = UserRegistrationDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withPassword("test123")
        .withMatchingPassword("test123")
        .withAccountType("COACH")
        .withEmail("test@coachy.life")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @Test
  public void registrationShouldReturnCreatedWhenValid() throws Exception {
    UserRegistrationDto dto = UserRegistrationDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withPassword("test123")
        .withMatchingPassword("test123")
        .withAccountType("COACH")
        .withEmail("test@coachy.life")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void registrationShouldReturn400WhenValidationError() throws Exception {
    UserRegistrationDto dto = UserRegistrationDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withPassword("test123")
        .withMatchingPassword("wrongPassword")
        .withAccountType("COACH")
        .withEmail("test@coachy.life")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");
  }

}
