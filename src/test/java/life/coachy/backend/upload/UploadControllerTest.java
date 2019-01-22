package life.coachy.backend.upload;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.flapdoodle.embed.process.io.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mock.web.MockMultipartFile;
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
public class UploadControllerTest {

  @Value("classpath:upload/")
  private Path uploadDirectoryPath;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;
  private MockMultipartFile pngImageFile;
  private MockMultipartFile jpegImageFile;
  private MockMultipartFile mp4ImageFile;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();

    this.pngImageFile = new MockMultipartFile("file", "something.png", "image/png", (byte[]) null);
    this.jpegImageFile = new MockMultipartFile("file", "something.jpg", "image/jpeg", (byte[]) null);
    this.mp4ImageFile = new MockMultipartFile("file", "something.mp4", "video/mp4", (byte[]) null);

    Map<String, Object> dbObjectDetails = new HashMap<String, Object>() {{
      this.put("username", "test918238901802306");
      this.put("email", "test@email.com");
      this.put("password", UploadControllerTest.this.passwordEncoder.encode("test123"));
      this.put("roles", Sets.newHashSet("USER", "ADMIN"));
    }};

    DBObject dbObject = new BasicDBObject(dbObjectDetails);
    this.mongoTemplate.insert(dbObject, "users");
  }

  @Test
  public void pngImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.pngImageFile)
        .param("target", "/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().is(200));
  }

  @Test
  public void jpegImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.jpegImageFile)
        .param("target", "/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().is(200));
  }

  @Test
  public void mp4ImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.mp4ImageFile)
        .param("target", "/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().is(415));
  }

  @Test
  public void downloadPngImageTest() throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.pngImageFile)
        .param("target", "/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andDo(result -> stringBuilder.append(result.getResponse().getContentAsString()))
        .andExpect(status().is(200));

    String fileUrl = new JSONObject(stringBuilder.toString()).get("fileUrl").toString();

    this.mockMvc.perform(MockMvcRequestBuilders.get(fileUrl.substring(fileUrl.lastIndexOf("/api")))
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().isOk());
  }

  @Test
  public void downloadJpegImageTest() throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.jpegImageFile)
        .param("target", "/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andDo(result -> stringBuilder.append(result.getResponse().getContentAsString()))
        .andExpect(status().is(200));

    String fileUrl = new JSONObject(stringBuilder.toString()).get("fileUrl").toString();

    this.mockMvc.perform(MockMvcRequestBuilders.get(fileUrl.substring(fileUrl.lastIndexOf("/api")))
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldFailIfFileDoesNotExists() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/uploads/?file=doesntExists.png&target=/tests")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test918238901802306", "test123")))
        .andExpect(status().isNotFound());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");

    Path path = Paths.get(this.uploadDirectoryPath + "/tests");
    if (java.nio.file.Files.exists(path)) {
      Files.forceDelete(Paths.get(this.uploadDirectoryPath + "/tests"));
    }
  }

}
