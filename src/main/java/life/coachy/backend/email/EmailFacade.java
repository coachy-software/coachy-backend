package life.coachy.backend.email;

import java.util.Locale;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailFacade {

  private final EmailCreationService emailCreationService;
  private final EmailService emailService;
  private final MessageSource messageSource;

  @Autowired
  public EmailFacade(EmailCreationService emailCreationService, EmailService emailService,
      MessageSource messageSource) {
    this.emailCreationService = emailCreationService;
    this.emailService = emailService;
    this.messageSource = messageSource;
  }

  public void sendResetPasswordEmail(String to, String resetLink) {
    try {
      String content = this.emailCreationService.createResetPasswordTemplateAsString(resetLink);
      String topic = this.messageSource.getMessage("email.passwordReset.topic", null, Locale.getDefault());
      this.emailService.sendMessage(to, topic, content);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
