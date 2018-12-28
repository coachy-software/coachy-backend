package life.coachy.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserRegistrationService {

  private final UserRepository userRepository;

  @Autowired
  public UserRegistrationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  UserRegistrationDto saveDto(UserRegistrationDto dto) {
    this.userRepository.save(dto.toEntity());
    return dto;
  }

}
