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
import life.coachy.backend.user.UserAuthenticationDto;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private AuthenticationController authenticationController;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MockMvc mockMvc;

  private BasicDBObject user;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.authenticationController).build();
    Map<String, Object> userDetails = new HashMap<String, Object>() {{
      this.put("username", "KzKX]9d>#s;7>.r{SQp-]M)s~_S");
      this.put("password", AuthenticationControllerIntegrationTest.this.passwordEncoder.encode("test"));
      this.put("roles", Sets.newHashSet("USER", "ADMIN"));
    }};

    this.user = new BasicDBObject(userDetails);
  }

  @After
  public void tearDown() throws Exception {
    if (this.user != null) {
      this.mongoTemplate.remove(this.user, "users");
    }
  }

  @Test
  public void authenticationTest() throws Exception {
    UserAuthenticationDto dto = new UserAuthenticationDto("KzKX]9d>#s;7>.r{SQp-]M)s~_S", "test");

    this.mongoTemplate.insert(this.user, "users");
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(dto.toJson().getBytes()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("KzKX]9d>#s;7>.r{SQp-]M)s~_S")));
  }

}
