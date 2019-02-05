package life.coachy.backend.exercise.template;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateUpdateDto;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateUpdateDtoBuilder;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExerciseTemplateControllerIntegrationTest {

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
    ExerciseTemplateBuilder builder = ExerciseTemplateBuilder.createBuilder()
        .withBriefDescription("brief")
        .withExampleImages(Lists.newArrayList("http://smh.com"))
        .withName("testName123")
        .withVerified(true);

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises?name=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName1234")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void searchShouldReturnEmptyArrayWhenNoMatches() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises?name=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  public void paginationPageSizeTest() throws Exception {
    ExerciseTemplateBuilder builder = ExerciseTemplateBuilder.createBuilder()
        .withBriefDescription("brief")
        .withExampleImages(Lists.newArrayList("http://smh.com"))
        .withName("testName123")
        .withVerified(true);

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises?page=0&size=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void paginationTest() throws Exception {
    ExerciseTemplateBuilder builder = ExerciseTemplateBuilder.createBuilder()
        .withBriefDescription("brief")
        .withExampleImages(Lists.newArrayList("http://smh.com"))
        .withName("testName123")
        .withVerified(true);
    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name", Matchers.is("testName1234")));
  }

  @Test
  public void updateShouldReturn403WhenNotAdmin() throws Exception {
    ObjectId id = ObjectId.get();

    ExerciseTemplate exerciseTemplate = ExerciseTemplateBuilder.createBuilder()
        .withIdentifier(id)
        .withBriefDescription("brief")
        .withExampleImages(Lists.newArrayList("http://smh.com"))
        .withName("testName123")
        .withVerified(true)
        .build();

    this.mongoTemplate.insert(exerciseTemplate, "exercises");
    ExerciseTemplateUpdateDto dto = ExerciseTemplateUpdateDtoBuilder.createBuilder()
        .withName("test123")
        .withExampleImages(Lists.newArrayList("dadada", "dajkdak"))
        .withBriefDescription("brief")
        .withVerified(true)
        .build();

    this.setUpUser(ObjectId.get(), "testUser123", "password123");
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/exercises/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUser123", "password123"))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void partialUpdateShouldReturn403WhenNotAdmin() throws Exception {
    ObjectId id = ObjectId.get();

    ExerciseTemplate exerciseTemplate = ExerciseTemplateBuilder.createBuilder()
        .withIdentifier(id)
        .withBriefDescription("brief")
        .withExampleImages(Lists.newArrayList("http://smh.com"))
        .withName("testName123")
        .withVerified(true)
        .build();

    this.mongoTemplate.insert(exerciseTemplate, "exercises");
    ExerciseTemplateUpdateDto dto = ExerciseTemplateUpdateDtoBuilder.createBuilder()
        .withName("test123")
        .withExampleImages(Lists.newArrayList("dadada", "dajkdak"))
        .withBriefDescription("brief")
        .withVerified(true)
        .build();

    this.setUpUser(ObjectId.get(), "testUser123", "password123");
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/exercises/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUser123", "password123"))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void deleteShouldReturn403WhenNotAdmin() throws Exception {
    this.setUpUser(ObjectId.get(), "testUser123", "password123");
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/exercises/{id}", ObjectId.get())
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("testUser123", "password123")))
        .andExpect(status().isForbidden());
  }

  private void setUpUser(ObjectId id, String username, String password) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("_id", id);
      this.put("username", username);
      this.put("password", ExerciseTemplateControllerIntegrationTest.this.passwordEncoder.encode(password));
      this.put("roles", Sets.newHashSet("USER"));
      this.put("something", "something");
    }};

    this.mongoTemplate.insert(new BasicDBObject(userDetails), "users");
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("exercises");
    this.mongoTemplate.dropCollection("users");
  }

}
