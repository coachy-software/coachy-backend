package life.coachy.backend.authentication;

import com.google.common.collect.Sets;
import life.coachy.backend.user.User;
import life.coachy.backend.user.UserBuilder;
import org.hamcrest.Matchers;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private AuthenticationController authenticationController;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;

  private User user;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.authenticationController).build();
    this.user = new UserBuilder()
        .withUsername("KzKX]9d>#s;7>.r{SQp-]M)s~_S")
        .withPassword(this.passwordEncoder.encode("test"))
        .withRoles(Sets.newHashSet("USER", "ADMIN"))
        .build();
  }

  @After
  public void tearDown() throws Exception {
    if (this.user != null) {
      this.mongoTemplate.remove(this.user);
    }
  }

  @Test
  public void authenticationTest() throws Exception {
    String jsonContent = "{\"username\": \"KzKX]9d>#s;7>.r{SQp-]M)s~_S\",\"password\": \"test\"}";

    this.mongoTemplate.insert(this.user);
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonContent))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("KzKX]9d>#s;7>.r{SQp-]M)s~_S")))
        .andExpect(jsonPath("$.roles[0]", Matchers.is("USER")))
        .andExpect(jsonPath("$.roles[1]", Matchers.is("ADMIN")));
  }

}
