package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.query.NotificationQueryDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NotificationFacade {

  private final NotificationService notificationService;
  private final NotificationCreator notificationCreator;

  NotificationFacade(NotificationService notificationService, NotificationCreator notificationCreator) {
    this.notificationService = notificationService;
    this.notificationCreator = notificationCreator;
  }

  public Page<NotificationQueryDto> fetchAllByRecipientId(ObjectId id, Pageable pageable) {
    return this.notificationService.fetchAllByRecipientId(id, pageable);
  }

}
