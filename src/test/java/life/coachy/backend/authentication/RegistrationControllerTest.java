package life.coachy.backend.authentication;


import life.coachy.backend.user.UserFacade;
import life.coachy.backend.user.UserRegistrationDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class RegistrationControllerTest {

  @InjectMocks
  private RegistrationController registrationController;

  @Mock
  private UserFacade userFacade;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.registrationController).build();
  }

  @Test
  public void registrationShouldReturnOkWhenValid() throws Exception {
    UserRegistrationDto dto = new UserRegistrationDto("VF#8&uYA;N{z&mT", "test123",
        "test123", "test@coachy.life", "test@coachy.life", "CHARGE");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void registrationShouldReturn400WhenValidationError() throws Exception {
    UserRegistrationDto dto = new UserRegistrationDto("VF#8&uYA;N{z&mT", "test",
        "test123", "test@coachy.life", "test@coachy.life", "CHARGE");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .content(dto.toJson().getBytes())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

}
