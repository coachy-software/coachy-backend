package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.query.NotificationQueryDto;
import life.coachy.backend.notification.query.NotificationQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NotificationFacade {

  private final NotificationQueryRepository queryRepository;
  private final NotificationRepository repository;
  private final NotificationCreator notificationCreator;

  NotificationFacade(NotificationQueryRepository queryRepository, NotificationRepository repository, NotificationCreator notificationCreator) {
    this.queryRepository = queryRepository;
    this.repository = repository;
    this.notificationCreator = notificationCreator;
  }

  public Page<NotificationQueryDto> fetchAllByRecipientId(ObjectId id, Pageable pageable) {
    return this.queryRepository.findAllByRecipientIdOrderByCreatedAtDesc(id, pageable);
  }

  public void save(NotificationMessageDto dto) {
    this.repository.save(this.notificationCreator.from(dto));
  }

}
