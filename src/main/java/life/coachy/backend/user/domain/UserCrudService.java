package life.coachy.backend.user.domain;

import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserCrudService {

  private final UserRepository userRepository;

  @Autowired
  public UserCrudService(UserRepository userRepository) {
    this.userRepository = userRepository;
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
