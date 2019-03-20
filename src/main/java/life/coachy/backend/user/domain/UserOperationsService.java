package life.coachy.backend.user.domain;

import java.util.Optional;
import life.coachy.backend.user.domain.exception.UserAlreadyExistsException;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserOperationsService {

  private final UserQueryDtoRepository userQueryDtoRepository;

  @Autowired
  public UserOperationsService(UserQueryDtoRepository userQueryDtoRepository) {
    this.userQueryDtoRepository = userQueryDtoRepository;
  }

  void checkIfUsernameAndEmailAlreadyExists(String username, String email, Runnable runnable) {
    if (this.userQueryDtoRepository.existsByUsernameOrEmail(username, email)) {
      throw new UserAlreadyExistsException();
    }
    runnable.run();
  }

  void checkIfUsernameAlreadyExists(ObjectId id, String username, Runnable runnable) {
    Optional<UserQueryDto> queryDto = this.userQueryDtoRepository.findById(id);
    boolean isUsernameEqualToPrincipalUsername = queryDto.isPresent() && username.equals(queryDto.get().getUsername());

    if (!queryDto.isPresent() || isUsernameEqualToPrincipalUsername) {
      runnable.run();
      return;
    }

    throw new UserAlreadyExistsException();
  }

  void checkIfExists(ObjectId id, Runnable runnable) {
    if (!this.userQueryDtoRepository.existsByIdentifier(id)) {
      throw new UserNotFoundException();
    }
    runnable.run();
  }

}
