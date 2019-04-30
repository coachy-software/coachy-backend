package life.coachy.backend.message;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
class MessagingEndpoint {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public MessagingEndpoint(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/chat.message.private")
  public void chatMessagePrivate(Message msg, Principal user) {
    this.simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/queue/private",
        new OutputMessage(user.getName(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date())));
  }

}
