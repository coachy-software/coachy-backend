/*
 * MIT License
 *
 * Copyright (c) 2018 Coachy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package life.coachy.backend.user.password;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.user.UserFacade;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordResetControllerIntegrationTest {

  @Autowired
  private PasswordResetController passwordResetController;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserFacade userFacade;

  private MockMvc mockMvc;
  private BasicDBObject dbObject;
  private String encodedPassword;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.passwordResetController).build();
    this.encodedPassword = PasswordResetControllerIntegrationTest.this.passwordEncoder.encode("test123$");

    Map<String, Object> dbObjectDetails = new HashMap<String, Object>() {{
      this.put("username", "test918238901802306");
      this.put("email", "test@email.com");
      this.put("password", PasswordResetControllerIntegrationTest.this.encodedPassword);
      this.put("roles", Sets.newHashSet("USER", "ADMIN"));
    }};

    this.dbObject = new BasicDBObject(dbObjectDetails);
  }

  @Test
  public void createTokenTest() throws Exception {
    this.mongoTemplate.insert(this.dbObject, "users");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/create-token/{email}", this.dbObject.get("email")))
        .andExpect(status().isNoContent());

    Query query = Query.query(Criteria.where("_id").is(this.dbObject.get("email")));
    boolean exists = this.mongoTemplate.exists(query, "password-tokens");

    assertTrue(exists);
  }

  @Test
  public void createTokenShouldReturn404WhenEmailNotFound() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/create-token/{email}", this.dbObject.get("email")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void createTokenShouldReturn409WhenAlreadyRequested() throws Exception {
    this.mongoTemplate.insert(this.dbObject, "users");

    Map<String, Object> tokenDetails = new HashMap<String, Object>() {{
      this.put("_id", PasswordResetControllerIntegrationTest.this.dbObject.get("email"));
    }};

    this.mongoTemplate.insert(new BasicDBObject(tokenDetails), "password-tokens");
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/create-token/{email}", this.dbObject.get("email")))
        .andExpect(status().isConflict());
  }

  @Test
  public void resetPasswordTest() throws Exception {
    this.mongoTemplate.insert(this.dbObject, "users");
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/create-token/{email}", this.dbObject.get("email")))
        .andExpect(status().isNoContent());

    Query query = Query.query(Criteria.where("_id").is(this.dbObject.get("email")));
    PasswordResetToken passwordResetToken = this.mongoTemplate.findOne(query, PasswordResetToken.class, "password-tokens");
    PasswordResetTokenDto passwordResetTokenDto = new PasswordResetTokenDto("newPassword", "newPassword");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reset-password/{token}", passwordResetToken.getToken())
        .content(passwordResetTokenDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    Query userQuery = Query.query(Criteria.where("password").is(this.encodedPassword));

    assertAll(
        () -> assertFalse(this.mongoTemplate.exists(userQuery, "users")),
        () -> assertFalse(this.mongoTemplate.exists(query, "password-tokens"))
    );
  }

  @Test
  public void resetPasswordShouldReturn404WhenTokenInvalid() throws Exception {
    PasswordResetTokenDto passwordResetTokenDto = new PasswordResetTokenDto("newPassword", "newPassword");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reset-password/{token}", "wrongToken")
        .content(passwordResetTokenDto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");
    this.mongoTemplate.dropCollection("password-tokens");
  }

}
