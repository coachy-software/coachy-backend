package life.coachy.backend.schedule;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import life.coachy.backend.schedule.day.dto.ScheduleDayDtoBuilder;
import life.coachy.backend.schedule.dto.ScheduleUpdateDto;
import life.coachy.backend.schedule.dto.ScheduleUpdateDtoBuilder;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserDtoBuilder;
import org.bson.types.ObjectId;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;


  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void searchTest() throws Exception {
    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(ObjectId.get()).build();
    ScheduleBuilder builder = ScheduleBuilder.createBuilder()
        .withName("testName123")
        .withCreator(userDto)
        .withCharge(userDto);

    this.setUpUser(ObjectId.get(), "testUsername", "testPassword", Sets.newHashSet());
    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?name=testName1234")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "testPassword")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName1234")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void searchShouldReturnEmptyArrayWhenNoMatches() throws Exception {
    this.setUpUser(ObjectId.get(), "testUsername", "testPassword", Sets.newHashSet());

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?name=testName1234")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "testPassword")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  public void paginationPageSizeTest() throws Exception {
    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(ObjectId.get()).build();
    ScheduleBuilder builder = ScheduleBuilder.createBuilder()
        .withName("testName123")
        .withCreator(userDto)
        .withCharge(userDto);

    this.setUpUser(ObjectId.get(), "testUsername", "testPassword", Sets.newHashSet());
    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?page=0&size=1")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "testPassword")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void paginationTest() throws Exception {
    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(ObjectId.get()).build();
    ScheduleBuilder builder = ScheduleBuilder.createBuilder()
        .withName("testName123")
        .withCreator(userDto)
        .withCharge(userDto);

    this.setUpUser(ObjectId.get(), "testUsername", "testPassword", Sets.newHashSet());

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?page=0&size=2")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUsername", "testPassword")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name", Matchers.is("testName1234")));
  }

  @Test
  public void updateShouldReturn401WhenUnlogged() throws Exception {
    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(ObjectId.get()).build();
    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(ObjectId.get())
        .withName("testName123")
        .withCreator(userDto)
        .build();

    this.mongoTemplate.insert(schedule, "schedules");
    ScheduleUpdateDto dto = ScheduleUpdateDtoBuilder.createBuilder()
        .withName("testName")
        .withActive(true)
        .withDays(Collections.singletonList(ScheduleDayDtoBuilder.createBuilder().build()))
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/schedules/{id}", ObjectId.get())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void partialUpdateShouldReturn401WhenUnlogged() throws Exception {
    ObjectId id = ObjectId.get();
    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(ObjectId.get()).build();

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(userDto)
        .build();
    ScheduleUpdateDto dto = ScheduleUpdateDtoBuilder.createBuilder()
        .withName("testName")
        .withActive(true)
        .withDays(Collections.singletonList(ScheduleDayDtoBuilder.createBuilder().build()))
        .build();

    this.mongoTemplate.insert(schedule, "schedules");
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/schedules/{id}", id)
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void deleteShouldReturn401WhenUnlogged() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/schedules/{id}", ObjectId.get()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void readActionShouldReturnForbiddenIfHasNoPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule.wrong_permission.read"));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isForbidden());
  }

  @Test
  public void readActionShouldReturnOkIfHasPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule." + id + ".read"));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateActionShouldReturnForbiddenIfHasNoPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    ScheduleUpdateDto dto = ScheduleUpdateDtoBuilder.createBuilder().withName("testName123").build();

    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule.wrong_permission.read"));
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/schedules/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void updateActionShouldReturnNoContentIfHasPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    ScheduleUpdateDto dto = ScheduleUpdateDtoBuilder.createBuilder().withName("testName123").build();

    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule." + id + ".update"));
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/schedules/{id}", id)
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void deleteActionShouldReturnForbiddenIfHasNoPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule.wrong_permission.read"));
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/schedules/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isForbidden());
  }

  @Test
  public void deleteActionShouldReturnNoContentIfHasPermissions() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    Schedule schedule = ScheduleBuilder.createBuilder()
        .withIdentifier(id)
        .withName("testName123")
        .withCreator(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCharge(UserDtoBuilder.createBuilder().withIdentifier(id).build())
        .withCreatedAt(LocalDateTime.now())
        .withUpdatedAt(LocalDateTime.now())
        .build();
    this.mongoTemplate.insert(schedule, "schedules");

    this.setUpUser(id, username, password, Sets.newHashSet("schedule." + id + ".delete"));
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/schedules/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isNoContent());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("schedules");
    this.mongoTemplate.dropCollection("users");
  }

  private void setUpUser(ObjectId id, String username, String password, Set<String> permissions) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("_id", id);
      this.put("username", username);
      this.put("password", ScheduleControllerIntegrationTest.this.passwordEncoder.encode(password));
      this.put("roles", Sets.newHashSet("USER"));
      this.put("permissions", permissions);
    }};

    this.mongoTemplate.insert(new BasicDBObject(userDetails), "users");
  }

}
