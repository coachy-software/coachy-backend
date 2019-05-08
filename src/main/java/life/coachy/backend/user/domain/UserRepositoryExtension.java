package life.coachy.backend.user.domain;

import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;

interface UserRepositoryExtension {

  void updatePermissionsById(ObjectId id, Set<String> permissions);

  void updatePermissionsByUsername(String username, Set<String> permissions);

  void updatePasswordByEmail(String email, String newPassword);

  void updateBoardIdById(ObjectId boardId, ObjectId userId);

}

class UserRepositoryExtensionImpl implements UserRepositoryExtension {

  private final MongoTemplate mongoTemplate;
  private final PasswordEncoder passwordEncoder;

  UserRepositoryExtensionImpl(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
    this.mongoTemplate = mongoTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void updatePermissionsById(ObjectId id, Set<String> permissions) {
    Query query = new Query(Criteria.where("_id").is(id));
    Update update = new Update();

    update.set("permissions", permissions);
    this.mongoTemplate.updateFirst(query, update, User.class);
  }

  @Override
  public void updatePermissionsByUsername(String username, Set<String> permissions) {
    Query query = new Query(Criteria.where("username").is(username));
    Update update = new Update();

    update.set("permissions", permissions);
    this.mongoTemplate.updateFirst(query, update, User.class);
  }

  @Override
  public void updatePasswordByEmail(String email, String newPassword) {
    Query query = new Query(Criteria.where("email").is(email));
    Update update = new Update();

    update.set("password", this.passwordEncoder.encode(newPassword));
    this.mongoTemplate.updateFirst(query, update, User.class);
  }

  @Override
  public void updateBoardIdById(ObjectId boardId, ObjectId userId) {
    Query query = new Query(Criteria.where("_id").is(userId));
    Update update = new Update();

    update.set("boardId", boardId);
    this.mongoTemplate.updateFirst(query, update, User.class);
  }

}
