package life.coachy.backend.user;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserRegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  UserRegistrationDto saveDto(UserRegistrationDto dto) {
    User user = dto.toEntity();

    user.setRoles(Sets.newHashSet("USER"));
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));

    this.userRepository.save(user);
    return dto;
  }

}
