package life.coachy.backend.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserCrudService {

  private final UserRepository userRepository;

  @Autowired
  public UserCrudService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user) {
    return this.userRepository.save(user);
  }

}
