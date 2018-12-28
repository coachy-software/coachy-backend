package life.coachy.backend.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  Optional<User> findUserByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

}
