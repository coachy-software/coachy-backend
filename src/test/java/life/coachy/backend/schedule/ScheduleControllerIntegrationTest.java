package life.coachy.backend.schedule;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import life.coachy.backend.user.UserDto;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleControllerIntegrationTest {

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
    // todo DTO IMPROVEMENTS!!!
    UserDto userDto = new UserDto(ObjectId.get(), null, null, null, null, null, null, null);

    ScheduleBuilder builder = new ScheduleBuilder()
        .withName("testName123")
        .withCreator(userDto);

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?name=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName1234")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void searchShouldReturnEmptyArrayWhenNoMatches() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?name=testName1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  public void paginationPageSizeTest() throws Exception {
    // todo DTO IMPROVEMENTS!!!
    UserDto userDto = new UserDto(ObjectId.get(), null, null, null, null, null, null, null);

    ScheduleBuilder builder = new ScheduleBuilder()
        .withName("testName123")
        .withCreator(userDto);

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?page=0&size=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name").doesNotExist());
  }

  @Test
  public void paginationTest() throws Exception {
    // todo DTO IMPROVEMENTS!!!
    UserDto userDto = new UserDto(ObjectId.get(), null, null, null, null, null, null, null);

    ScheduleBuilder builder = new ScheduleBuilder()
        .withName("testName123")
        .withCreator(userDto);

    this.mongoTemplate.insertAll(Arrays.asList(builder.build(), builder.withName("testName1234").build()));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.not(Matchers.emptyArray())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is("testName123")))
        .andExpect(jsonPath("$.content[1].name", Matchers.is("testName1234")));
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("schedules");
  }

}
