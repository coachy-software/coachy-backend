package life.coachy.backend.user;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserRegistrationDto;
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
    Preconditions.checkNotNull(dto, "User registration DTO cannot be null");

    User user = UserMapper.INSTANCE.userRegistrationDtoToUser(dto);
    UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);

    user.setRoles(Sets.newTreeSet(Collections.singletonList("USER")));
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    user.setPermissions(new HashSet<>());

    this.userRepository.save(user);
    return dto;
  }

}
