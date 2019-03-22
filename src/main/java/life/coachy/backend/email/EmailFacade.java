package life.coachy.backend.email;

import java.util.Locale;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailFacade {

  private final EmailCreationService emailCreationService;
  private final EmailSender emailSender;
  private final MessageSource messageSource;

  @Value("${frontend.uri}")
  private String frontendUri;

  @Autowired
  public EmailFacade(EmailCreationService emailCreationService, EmailSender emailSender, MessageSource messageSource) {
    this.emailCreationService = emailCreationService;
    this.emailSender = emailSender;
    this.messageSource = messageSource;
  }

  public void sendResetPasswordEmail(String to, String token) {
    try {
      String link = this.frontendUri + "reset-password/" + token;
      String content = this.emailCreationService.createResetPasswordTemplateAsString(link);
      String topic = this.messageSource.getMessage("email.passwordReset.topic", null, Locale.getDefault());
      this.emailSender.sendMessage(to, topic, content);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
