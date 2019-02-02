package life.coachy.backend.exercise.template;

import com.google.common.collect.Lists;
import java.util.Arrays;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
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

  private MockMvc mockMvc;


  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void searchTest() throws Exception {
    ExerciseTemplateBuilder builder = new ExerciseTemplateBuilder()
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
    ExerciseTemplateBuilder builder = new ExerciseTemplateBuilder()
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
    ExerciseTemplateBuilder builder = new ExerciseTemplateBuilder()
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

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("exercises");
  }

}
