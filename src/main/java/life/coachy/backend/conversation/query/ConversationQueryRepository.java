package life.coachy.backend.conversation.query;

import java.util.List;
import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface ConversationQueryRepository extends QueryFetchOneRepository<ConversationQueryDto, ObjectId>, Repository<ConversationQueryDto, ObjectId> {

  Page<ConversationQueryDto> findAllByConversersContainsOrderByLastMessageDateDesc(List<String> conversers, Pageable pageable);

  Optional<ConversationQueryDto> findByConversersContains(List<String> conversers);

}
