package life.coachy.backend.board;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import life.coachy.backend.board.dto.BoardCreateDto;
import life.coachy.backend.board.dto.BoardUpdateDto;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoardControllerIntegrationTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MongoTemplate mongoTemplate;

  private MockMvc mockMvc;


  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void readActionTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    this.mongoTemplate.insert(BoardBuilder.createBuilder().withIdentifier(id).build(), "boards");
    this.setUpUser(id, username, password, Sets.newHashSet("board." + id + ".read"));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/boards/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }


  @Test
  public void readActionInvalidPermissionsTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    this.mongoTemplate.insert(BoardBuilder.createBuilder().withIdentifier(id).build(), "boards");
    this.setUpUser(ObjectId.get(), username, password, Sets.newHashSet("board." + id + ".wrong"));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/boards/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void createActionTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    UserDto userDto = UserDtoBuilder.createBuilder().withIdentifier(id).withPermissions(Sets.newHashSet()).build();
    BoardCreateDto dto = new BoardCreateDto("testName", Lists.newArrayList(new LabelDto()), userDto);

    this.mongoTemplate.insert(BoardBuilder.createBuilder().withIdentifier(id).build(), "boards");
    this.setUpUser(id, username, password, Sets.newHashSet());

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/boards")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .contentType(MediaType.APPLICATION_JSON)
        .content(dto.toJson()))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    Query query = Query.query(Criteria.where("name").is("testName"));
    Assertions.assertTrue(this.mongoTemplate.exists(query, "boards"));
  }

  @Test
  public void updateActionTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    BoardUpdateDto dto = new BoardUpdateDto("testNameEdited", Lists.newArrayList(new LabelDto()));
    Board board = BoardBuilder.createBuilder().withIdentifier(id).withName("testName").build();

    this.mongoTemplate.insert(board, "boards");
    this.setUpUser(ObjectId.get(), username, password, Sets.newHashSet("board." + id + ".update"));

    Query beforeQuery = Query.query(Criteria.where("name").is("testName"));
    Assertions.assertTrue(this.mongoTemplate.exists(beforeQuery, "boards"));

    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/boards/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .contentType(MediaType.APPLICATION_JSON)
        .content(dto.toJson()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());

    Query afterQuery = Query.query(Criteria.where("name").is("testNameEdited"));
    Assertions.assertTrue(this.mongoTemplate.exists(afterQuery, "boards"));
  }

  @Test
  public void updateActionInvalidPermissionsTest() throws Exception {
    ObjectId id = ObjectId.get();
    String username = "testUsername";
    String password = "testPassword";

    BoardCreateDto dto = new BoardCreateDto("testNameEdited", Lists.newArrayList(new LabelDto()), new UserDto());
    Board board = BoardBuilder.createBuilder().withIdentifier(id).withName("testName").build();

    this.mongoTemplate.insert(board, "boards");
    this.setUpUser(ObjectId.get(), username, password, Sets.newHashSet());

    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/boards/{id}", id)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password))
        .contentType(MediaType.APPLICATION_JSON)
        .content(dto.toJson()))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  private void setUpUser(ObjectId id, String username, String password, Set<String> permissions) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("_id", id);
      this.put("username", username);
      this.put("password", BoardControllerIntegrationTest.this.passwordEncoder.encode(password));
      this.put("roles", Sets.newHashSet("USER"));
      this.put("permissions", permissions);
    }};

    this.mongoTemplate.insert(new BasicDBObject(userDetails), "users");
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("boards");
    this.mongoTemplate.dropCollection("users");
  }

}
