package life.coachy.backend.conversation.query;

import java.util.List;
import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.Repository;

public interface ConversationQueryRepository extends QueryFetchOneRepository<ConversationQueryDto, ObjectId>, Repository<ConversationQueryDto, ObjectId>,
    ConversationQueryRepositoryExtension {

}

interface ConversationQueryRepositoryExtension {

  Page<ConversationQueryDto> findAllByConversersContainsOrderByLastMessageDateDesc(List<String> conversers, Pageable pageable);

  Optional<ConversationQueryDto> findByConversersContains(List<String> conversers); // todo custom impl ;/

}

class ConversationQueryRepositoryExtensionImpl implements ConversationQueryRepositoryExtension {

  private final MongoTemplate mongoTemplate;

  ConversationQueryRepositoryExtensionImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<ConversationQueryDto> findByConversersContains(List<String> conversers) {
    Query query = Query.query(Criteria.where("conversers").all(conversers));
    return Optional.ofNullable(this.mongoTemplate.findOne(query, ConversationQueryDto.class));
  }

  @Override
  public Page<ConversationQueryDto> findAllByConversersContainsOrderByLastMessageDateDesc(List<String> conversers, Pageable pageable) {
    Query query = Query.query(Criteria.where("conversers").all(conversers)).with(pageable);
    List<ConversationQueryDto> queryDtos = this.mongoTemplate.find(query, ConversationQueryDto.class);
    
    long count = this.mongoTemplate.count(query, ConversationQueryDto.class);
    return new PageImpl<>(queryDtos, pageable, count);
  }

}
