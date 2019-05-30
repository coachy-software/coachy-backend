package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.query.NotificationQueryDto;
import life.coachy.backend.notification.query.NotificationQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class NotificationService {

  private final NotificationQueryRepository queryRepository;

  @Autowired
  NotificationService(NotificationQueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  Page<NotificationQueryDto> fetchAllByRecipientId(ObjectId id, Pageable pageable) {
    return this.queryRepository.findAllByRecipientIdOrderByCreatedAtDesc(id, pageable);
  }

}
