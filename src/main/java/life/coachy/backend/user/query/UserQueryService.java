package life.coachy.backend.user.query;

import life.coachy.backend.user.domain.exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService {

  private final UserQueryRepository repository;

  @Autowired
  public UserQueryService(UserQueryRepository repository) {
    this.repository = repository;
  }

  public UserQueryDto fetchOne(ObjectId id) {
    return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
  }

}
