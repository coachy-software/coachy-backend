package life.coachy.backend.notification;

import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
class NotificationCommand {

  private final NotificationFacade notificationFacade;

  @Autowired
  NotificationCommand(NotificationFacade notificationFacade) {
    this.notificationFacade = notificationFacade;
  }

  @ShellMethod(value = "Sends a notification", key = "notification")
  public String sendNotification(ObjectId recipientId, String message) {
    NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
        .withRecipientId(recipientId)
        .withSenderName("Coachy")
        .withContent(message)
        .withType("ALERT")
        .build();

    this.notificationFacade.sendNotification(dto);
    return "The message has been sent to: " + dto.getRecipientId() + " with content: " + dto.getContent();
  }

}
