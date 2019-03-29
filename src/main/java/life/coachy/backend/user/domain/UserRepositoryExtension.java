package life.coachy.backend.user.domain;

import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;

interface UserRepositoryExtension {

  void updateEntireEntity(ObjectId id, Map<String, Object> propertiesToUpdate);

  void updatePermissionsById(ObjectId id, Set<String> permissions);

  void updatePasswordByEmail(String email, String newPassword);

}

