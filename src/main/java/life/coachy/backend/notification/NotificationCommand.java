package life.coachy.backend.notification;

import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
class NotificationCommand {

  private final NotificationFacade notificationFacade;
  private final ObjectToJsonConverter toJsonConverter;

  @Autowired
  NotificationCommand(NotificationFacade notificationFacade, ObjectToJsonConverter toJsonConverter) {
    this.notificationFacade = notificationFacade;
    this.toJsonConverter = toJsonConverter;
  }

  @ShellMethod(value = "Sends a notification", key = "send")
  public String sendNotification(ObjectId recipientId, String message) {
    NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
        .withRecipientId(recipientId)
        .withSenderName("Coachy")
        .withContent(this.toJsonConverter.convert(this.notificationFacade.convertAlertContentToJson(message, "/notifications")))
        .withType("ALERT")
        .withCreatedAt(LocalDateTime.now())
        .build();

    this.notificationFacade.sendNotificationToUser(dto);
    return "The message has been sent to: " + dto.getRecipientId() + " with content: " + dto.getContent();
  }

  @ShellMethod(value = "Sends a notification", key = "sendall")
  public String sendNotification(String message) {
    NotificationMessageDtoBuilder dtoBuilder = NotificationMessageDtoBuilder.create()
        .withSenderName("Coachy")
        .withContent(message)
        .withCreatedAt(LocalDateTime.now())
        .withType("ALERT");

    this.notificationFacade.sendNotificationToAllUsers(dtoBuilder);
    return "The message: " + dtoBuilder.build().getContent() + " has been sent to everyone.";
  }

}
