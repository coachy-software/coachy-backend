package life.coachy.backend.message;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
class MessagingEndpoint {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public MessagingEndpoint(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @SendToUser("/queue/private")
  @MessageMapping("/chat.message.private")
  public OutputMessage chatMessagePrivate(Message msg, Principal user) {
    System.out.println("user: " + user);
    return new OutputMessage(user.getName(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
  }

}
