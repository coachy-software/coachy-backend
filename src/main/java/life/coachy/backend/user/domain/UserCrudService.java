package life.coachy.backend.user.domain;

import java.util.List;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserCrudService {

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;

  @Autowired
  UserCrudService(UserRepository userRepository, UserQueryRepository userQueryRepository) {
    this.userRepository = userRepository;
    this.userQueryRepository = userQueryRepository;
  }

  List<UserQueryDto> fetchAll() {
    return this.userQueryRepository.findAll();
  }

  UserQueryDto fetchOne(ObjectId id) {
    return this.userQueryRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  UserQueryDto fetchOne(String username) {
    return this.userQueryRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
  }

  User save(User user) {
    return this.userRepository.save(user);
  }

  void delete(ObjectId id) {
    this.userRepository.deleteById(id);
  }

  void update(UserQueryDto queryDto, User user) {
    user.setIdentifier(queryDto.getIdentifier());
    user.setAccountType(AccountType.valueOf(queryDto.getAccountType().name()));
    user.setPermissions(queryDto.getPermissions());
    user.setRoles(queryDto.getRoles());
    user.setPassword(queryDto.getPassword());
    user.setBoardId(queryDto.getBoardId());

    this.userRepository.save(user);
  }

}
