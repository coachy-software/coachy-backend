package life.coachy.backend.util;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.util.crud.TestDto;
import life.coachy.backend.util.crud.TestEntity;
import life.coachy.backend.util.crud.TestEntityController;
import life.coachy.backend.util.crud.TestRepository;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class AbstractCrudControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private TestEntityController testEntityController;

  @Autowired
  private TestRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void readShouldReturn404WhenDoesNotExists() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/tests/{id}", ObjectId.get()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void readTest() throws Exception {
    ObjectId id = ObjectId.get();
    this.repository.save(new TestEntity(id, "testEntity1127", "something"));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/tests/{id}", id.toHexString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("testEntity1127")))
        .andExpect(jsonPath("$.identifier", Matchers.is(id.toHexString())));
  }

  @Test
  public void createTest() throws Exception {
    TestDto testDto = new TestDto("testEntity1128", "something");
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tests")
        .content(testDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username", Matchers.is("testEntity1128")));
  }

  @Test
  public void createShouldReturn409WhenAlreadyExists() throws Exception {
    TestDto testDto = new TestDto("testEntity1129", "something");
    this.repository.save(new TestEntity(ObjectId.get(), "testEntity1129", "something"));

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tests")
        .content(testDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @Test
  public void createShouldReturn400WhenValidationError() throws Exception {
    TestDto testDto = new TestDto(null, null);

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tests")
        .content(testDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity123132";
    String password = "test123";

    TestDto testDto = new TestDto(username + "_EDITED", "something");
    TestEntity testEntity = new TestEntity(id, username, "something");
    this.repository.save(testEntity);

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(testDto.toJson())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    Query query = Query.query(Criteria.where("username").is(testDto.getUsername()));
    TestEntity databaseEntity = this.mongoTemplate.findOne(query, TestEntity.class);

    assertAll(
        () -> assertNotNull(databaseEntity),
        () -> assertNotEquals(databaseEntity, testEntity),
        () -> assertEquals(username + "_EDITED", databaseEntity.getUsername())
    );
  }

  @Test
  public void updateShouldCreateNewEntityWhenEntityNotFound() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity3901028930";
    String password = "test123";

    TestDto testDto = new TestDto(username + "_EDITED", "something");
    TestEntity testEntity = new TestEntity(id, username, "something");

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(testDto.toJson())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    Query query = Query.query(Criteria.where("username").is(testDto.getUsername()));
    TestEntity databaseEntity = this.mongoTemplate.findOne(query, TestEntity.class);

    assertAll(
        () -> assertNotNull(databaseEntity),
        () -> assertNotEquals(databaseEntity, testEntity),
        () -> assertEquals(testDto.getUsername(), databaseEntity.getUsername())
    );
  }

  @Test
  public void updateShouldReturn403WhenDifferentUser() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity30182u8038";
    String password = "test123";

    TestDto testDto = new TestDto(username + "_EDITED", "something");
    TestEntity testEntity = new TestEntity(id, username, "something");
    this.repository.save(testEntity);

    this.setUpUser(ObjectId.get(), username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(testDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void updateShouldReturn400WhenValidationError() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity30182u8038";
    String password = "test123";

    TestDto testDto = new TestDto("", "");
    TestEntity testEntity = new TestEntity(id, username, "something");
    this.repository.save(testEntity);

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(testDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void partialUpdateTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity123131";
    String password = "test123";

    TestDto dto = new TestDto(null, "something_EDITED");
    TestEntity testEntity = new TestEntity(id, username, "something");
    this.repository.save(testEntity);

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    Query query = Query.query(Criteria.where("username").is(username));
    TestEntity databaseEntity = this.mongoTemplate.findOne(query, TestEntity.class);

    assertAll(
        () -> assertNotNull(databaseEntity),
        () -> assertNotNull(databaseEntity.getSomething()),
        () -> assertEquals(databaseEntity.getSomething(), "something_EDITED"),
        () -> assertEquals(databaseEntity.getUsername(), username)
    );
  }

  @Test
  public void partialUpdateShouldReturn404WhenEntityNotFound() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity3901903190";
    String password = "test123";

    TestDto dto = new TestDto(null, "something_EDITED");
    TestEntity testEntity = new TestEntity(id, username, "something");

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void partialUpdateShouldReturn403WhenDifferentUser() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity3901903190";
    String password = "test123";

    TestDto dto = new TestDto(null, "something_EDITED");

    this.setUpUser(ObjectId.get(), username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void deleteTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity12313";
    String password = "test123";

    this.mongoTemplate.insert(new TestEntity(id, username, "something"));

    Query query = Query.query(Criteria.where("username").is(username));
    assertNotNull(this.mongoTemplate.findOne(query, TestEntity.class));

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isNoContent());

    assertNull(this.mongoTemplate.findOne(query, TestEntity.class));
  }

  @Test
  public void deleteShouldReturn404WhenEntityNotFound() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testEntity123812";
    String password = "test123";

    this.setUpUser(id, username, password);
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/tests/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(status().isNotFound());
  }

  private void setUpUser(ObjectId id, String username, String password) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("_id", id);
      this.put("username", username);
      this.put("password", AbstractCrudControllerIntegrationTest.this.passwordEncoder.encode(password));
      this.put("roles", Sets.newHashSet("USER"));
      this.put("something", "something");
    }};

    this.mongoTemplate.insert(new BasicDBObject(userDetails), "users");
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("tests");
    this.mongoTemplate.dropCollection("users");
  }

}
