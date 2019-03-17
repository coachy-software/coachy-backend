package life.coachy.backend.old_user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import life.coachy.backend.old_user.dto.UserUpdateDto;
import life.coachy.backend.old_user.dto.UserUpdateDtoBuilder;
import org.bson.types.ObjectId;
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
  private UserCrudService userCrudService;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void authenticationTest() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")))
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("test12312313")));
  }

  @Test
  public void authenticationShouldReturn401WhenBadCredentials() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test1234")))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void updateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("testUsername")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")))
        .withEmail("test@email.com")
        .build();
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .withBoardIdentifier(ObjectId.get())
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void partialUpdateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")))
        .withEmail("test@email.com")
        .build();
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void updateShouldReturn409WhenUsernameExits() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("testUsername")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")))
        .withEmail("test@email.com")
        .build();
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test2@email.com")
        .withAvatar("http://www.com.com")
        .withBoardIdentifier(ObjectId.get())
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void partialUpdateShouldReturn409WhenUsernameAlreadyExits() throws Exception {
    User user = UserBuilder.createBuilder()
        .withUsername("testUsername")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")))
        .withEmail("test@email.com")
        .build();
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test2@email.com")
        .withAvatar("http://www.com.com")
        .build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void searchTest() throws Exception {
    UserBuilder builder = UserBuilder.createBuilder()
        .withUsername("testName123");

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withUsername("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users?username=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].username", Matchers.is("testName1234")))
        .andExpect(jsonPath("$.content[1].username").doesNotExist());
  }

  @Test
  public void searchShouldReturnEmptyArrayWhenNoMatches() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users?name=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  public void paginationPageSizeTest() throws Exception {
    UserBuilder builder = UserBuilder.createBuilder()
        .withUsername("testName123");

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withUsername("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users?page=0&size=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].username", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].username").doesNotExist());
  }

  @Test
  public void paginationTest() throws Exception {
    UserBuilder builder = UserBuilder.createBuilder()
        .withUsername("testName123");

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withUsername("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].username", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].username", Matchers.is("testName1234")));
  }

  @Test
  public void updateShouldReturn403WhenDifferentUser() throws Exception {
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .build();
    UserBuilder userBuilder = UserBuilder.createBuilder()
        .withIdentifier(ObjectId.get())
        .withUsername("testUsername")
        .withPassword("password123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")));

    User user = new User(userBuilder);

    this.userCrudService.savePassword(user, user.getPassword());
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", ObjectId.get())
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getUsername(), "password123"))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void partialUpdateShouldReturn403WhenDifferentUser() throws Exception {
    UserUpdateDto dto = UserUpdateDtoBuilder.createBuilder()
        .withUsername("testUsername")
        .withDisplayName("testDisplayName")
        .withPassword("testPassword")
        .withEmail("test@email.com")
        .withAvatar("http://www.com.com")
        .build();
    UserBuilder userBuilder = UserBuilder.createBuilder()
        .withIdentifier(ObjectId.get())
        .withUsername("testUsername")
        .withPassword("password123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")));

    User user = userBuilder.build();
    User secondUser = userBuilder.withIdentifier(ObjectId.get()).withUsername("testUsername2").build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.userCrudService.savePassword(secondUser, secondUser.getPassword());

    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/{id}", secondUser.getIdentifier())
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "password123"))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void deleteShouldReturn403WhenDifferentUser() throws Exception {
    UserBuilder userBuilder = UserBuilder.createBuilder()
        .withIdentifier(ObjectId.get())
        .withUsername("testUsername")
        .withPassword("password123")
        .withRoles(Sets.newTreeSet(Lists.newArrayList("USER")));

    User user = userBuilder.build();
    User secondUser = userBuilder.withIdentifier(ObjectId.get()).withUsername("testUsername2").build();

    this.userCrudService.savePassword(user, user.getPassword());
    this.userCrudService.savePassword(secondUser, secondUser.getPassword());

    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", secondUser.getIdentifier())
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "password123")))
        .andExpect(status().isForbidden());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");
  }

}
