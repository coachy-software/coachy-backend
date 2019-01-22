package life.coachy.backend.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserControllerIntegrationTest {

  @Autowired
  private WebApplicationContext applicationContext;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserService userService;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void authenticationTest() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .build();

    this.userService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("test12312313")));
  }

  @Test
  public void authenticationShouldReturn401WhenBadCredentials() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .build();

    this.userService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test1234")))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void updateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .withEmail("test@email.com")
        .build();

    this.userService.savePassword(user, user.getPassword());
    UserCrudDto dto = new UserCrudDto("test6534635", "test123", "test@email.com", "http://www.coachy.life/");

    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void partialUpdateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .withEmail("test@email.com")
        .build();

    this.userService.savePassword(user, user.getPassword());
    UserCrudDto dto = new UserCrudDto(null, null, "test@email.com", null);

    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isConflict());
  }


  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");
  }

}
