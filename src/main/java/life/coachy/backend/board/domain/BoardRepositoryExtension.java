package life.coachy.backend.board.domain;

import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

interface BoardRepositoryExtension {

  void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate);

}

class BoardRepositoryExtensionImpl implements BoardRepositoryExtension {

  private final MongoTemplate mongoTemplate;

  public BoardRepositoryExtensionImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate) {
    Query query = new Query(Criteria.where("_id").is(id));
    Update update = new Update();

    propertiesToUpdate.forEach(update::set);

    this.mongoTemplate.updateFirst(query, update, Board.class);
  }

}
