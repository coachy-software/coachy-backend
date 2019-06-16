package life.coachy.backend.notification.domain;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.query.NotificationQueryDto;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
class NotificationService {

  private final NotificationRepository repository;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  NotificationService(NotificationRepository repository, SimpMessagingTemplate simpMessagingTemplate) {
    this.repository = repository;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  void sendNotification(Notification notification, NotificationMessageDto dto, UserQueryDto recipientUserQueryDto) {
    this.simpMessagingTemplate.convertAndSendToUser(recipientUserQueryDto.getUsername(), "/queue/notifications", dto);
    this.save(notification);
  }

  void markAsRead(Notification notification, NotificationQueryDto dto) {
    notification.setRead(true);
    notification.setIdentifier(dto.getIdentifier());

    this.save(notification);
  }

  Map<String, String> convertAlertContentToJson(String message, String link) {
    return Maps.newHashMap(new HashMap<String, String>() {{
      this.put("link", link);
      this.put("text", message);
    }});
  }

  private void save(Notification notification) {
    this.repository.save(notification);
  }

}
