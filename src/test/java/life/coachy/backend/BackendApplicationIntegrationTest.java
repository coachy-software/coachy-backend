package life.coachy.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class BackendApplicationIntegrationTest {

  @Autowired
  private WebSecurityConfiguration webSecurityConfiguration;

  @Test
  public void contextLoads() {
    Assert.assertNotNull(this.webSecurityConfiguration);
  }

}

