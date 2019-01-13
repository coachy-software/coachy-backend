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

package life.coachy.backend.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Sets;
import life.coachy.backend.util.crud.TestDto;
import life.coachy.backend.util.crud.TestEntity;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerIntegrationTest {

  @Autowired
  private UserController userController;

  @Autowired
  private WebApplicationContext applicationContext;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserService userService;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void authenticationTest() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .build();

    this.userService.save(user);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("test12312313")));
  }

  @Test
  public void authenticationShouldReturn401WhenBadCredentials() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .build();

    this.userService.save(user);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test1234")))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void updateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .withEmail("test@email.com")
        .build();

    this.userService.save(user);
    UserCrudDto dto = new UserCrudDto("test6534635", "test123", "test@email.com", "http://www.coachy.life/");

    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isConflict());
  }

  @Test
  public void partialUpdateShouldReturn409WhenEmailAlreadyExits() throws Exception {
    User user = new UserBuilder()
        .withUsername("test12312313")
        .withAccountType(AccountType.COACH)
        .withPassword("test123")
        .withRoles(Sets.newHashSet("ADMIN"))
        .withEmail("test@email.com")
        .build();

    this.userService.save(user);
    UserCrudDto dto = new UserCrudDto(null, null, "test@email.com", null);

    this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/{id}", user.getIdentifier())
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.httpBasic("test12312313", "test123")))
        .andExpect(status().isConflict());
  }


  @After
  public void tearDown() throws Exception {
    this.mongoTemplate.dropCollection("users");
  }

}
