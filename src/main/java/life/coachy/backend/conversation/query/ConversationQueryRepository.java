package life.coachy.backend.conversation.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface ConversationQueryRepository extends QueryFetchOneRepository<ConversationQueryDto, ObjectId>, Repository<ConversationQueryDto, ObjectId> {

  Set<ConversationQueryDto> findAllByRecipientIdOrSenderIdOrderByLastMessageDateDesc(ObjectId recipientId, ObjectId senderId);

}
