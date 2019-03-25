package life.coachy.backend.user.domain;

import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;

interface UserRepositoryExtension {

  void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate);
  void updatePermissionsById(ObjectId id, Set<String> permissions);
  void updatePasswordByEmail(String email, String newPassword);

}

