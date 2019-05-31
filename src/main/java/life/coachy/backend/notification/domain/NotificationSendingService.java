package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.user.query.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
class NotificationSendingService {

  private final NotificationRepository repository;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  NotificationSendingService(NotificationRepository repository, SimpMessagingTemplate simpMessagingTemplate) {
    this.repository = repository;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  void sendNotification(Notification notification, NotificationMessageDto dto, UserQueryDto recipientUserQueryDto) {
    this.simpMessagingTemplate.convertAndSendToUser(recipientUserQueryDto.getUsername(), "/queue/notifications", dto);
    this.save(notification);
  }

  private void save(Notification notification) {
    this.repository.save(notification);
  }

}
