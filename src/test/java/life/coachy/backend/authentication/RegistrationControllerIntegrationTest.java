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

package life.coachy.backend.authentication;

import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.user.UserRegistrationDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrationControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private RegistrationController registrationController;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;
  private BasicDBObject user;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.registrationController).build();
  }

  @Test
  public void registrationShouldReturn409WhenUserAlreadyExists() throws Exception {
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("username", "test918238901802301");
      this.put("password", RegistrationControllerIntegrationTest.this.passwordEncoder.encode("test"));
      this.put("roles", Sets.newHashSet("USER", "ADMIN"));
    }};

    this.user = new BasicDBObject(userDetails);
    this.mongoTemplate.insert(this.user, "users");

    UserRegistrationDto dto = new UserRegistrationDto("test918238901802301", "test123",
        "test123", "test@coachy.life", "test@coachy.life", "COACH");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @After
  public void tearDown() throws Exception {
    if (this.user != null) {
      this.mongoTemplate.remove(this.user, "users");
    }
  }

}
