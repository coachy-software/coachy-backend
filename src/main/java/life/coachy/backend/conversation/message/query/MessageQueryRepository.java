package life.coachy.backend.conversation.message.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface MessageQueryRepository extends QueryFetchOneRepository<MessageQueryDto, ObjectId>, Repository<MessageQueryDto, ObjectId> {

  boolean existsByConversationId(ObjectId conversationId);

  Set<MessageQueryDto> findAllByConversationId(ObjectId conversationId);

}
