package life.coachy.backend.user.domain;

import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;

interface UserUpdateRepositoryExtension {

  void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate);

}

class UserUpdateRepositoryExtensionImpl implements UserUpdateRepositoryExtension {

  private final MongoTemplate mongoTemplate;
  private final PasswordEncoder passwordEncoder;

  UserUpdateRepositoryExtensionImpl(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
    this.mongoTemplate = mongoTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate) {
    Query query = new Query(Criteria.where("_id").is(id));
    Update update = new Update();

    propertiesToUpdate.forEach((key, value) -> {
      if ("password".equals(key)) {
        value = this.passwordEncoder.encode(String.valueOf(value));
      }
      update.set(key, value);
    });
    
    this.mongoTemplate.updateFirst(query, update, User.class);
  }

}
