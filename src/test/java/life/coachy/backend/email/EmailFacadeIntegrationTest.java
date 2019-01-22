package life.coachy.backend.email;


import javax.mail.internet.MimeMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailFacadeIntegrationTest {

  @Autowired
  private EmailFacade emailFacade;

  @Rule
  public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

  @Test
  public void emailFacadeTest() {
    this.emailFacade.sendResetPasswordEmail("test@coachy.life", "http://localhost/reset");
    MimeMessage[] receivedMessages = this.smtpServerRule.getMessages();

    assertAll(
        () -> assertEquals(1, receivedMessages.length),
        () -> assertEquals("test@coachy.life", receivedMessages[0].getAllRecipients()[0].toString())
    );
  }

}
