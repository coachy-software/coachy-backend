package life.coachy.backend.base

import groovy.transform.TypeChecked
import life.coachy.backend.infrastructure.constants.Profiles
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@TypeChecked
@SpringBootTest
@ActiveProfiles([Profiles.TEST])
class IntegrationSpec extends Specification {

  @Autowired
  protected WebApplicationContext webApplicationContext;

  MockMvc mockMvc

  @Before
  void setupMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build()
  }


}
