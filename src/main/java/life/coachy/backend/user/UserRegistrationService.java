package life.coachy.backend.user;

import com.google.common.collect.Sets;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserRegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDtoMapperFactory mapperFactory;

  @Autowired
  public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      UserDtoMapperFactory mapperFactory) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.mapperFactory = mapperFactory;
  }

  UserRegistrationDto saveDto(UserRegistrationDto dto) {
    User user = this.mapperFactory.obtainEntity(dto);

    user.setRoles(Sets.newTreeSet(Collections.singletonList("USER")));
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));

    this.userRepository.save(user);
    return dto;
  }

}
