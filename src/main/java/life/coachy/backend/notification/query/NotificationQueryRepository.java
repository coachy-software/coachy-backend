package life.coachy.backend.notification.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface NotificationQueryRepository extends QueryFetchOneRepository<NotificationQueryDto, ObjectId>, Repository<NotificationQueryDto, ObjectId> {

  Page<NotificationQueryDto> findAllByRecipientIdOrderByCreatedAtDesc(ObjectId recipientId, Pageable pageable);

  Set<NotificationQueryDto> findAllByRecipientId(ObjectId recipientId);

  boolean existsByRecipientIdAndReadIsFalse(ObjectId recipientId);

}
