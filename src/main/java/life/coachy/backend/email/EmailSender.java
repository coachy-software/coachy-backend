package life.coachy.backend.email;

import com.google.common.base.Preconditions;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class EmailSender {

  private final JavaMailSender emailSender;

  @Autowired
  public EmailSender(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Async
  public void sendMessage(String to, String subject, String text) throws MessagingException {
    Preconditions.checkNotNull(to, "Recipient email address cannot be null");
    Preconditions.checkNotNull(subject, "Subject cannot be null");
    Preconditions.checkNotNull(text, "Email message content cannot be null!");

    MimeMessage mimeMessage = this.emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);

    this.emailSender.send(mimeMessage);
  }

}
