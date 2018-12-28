package life.coachy.backend.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

  private final UserService userService;
  private final UserRegistrationService userRegistrationService;

  @Autowired
  public UserFacade(UserService userService, UserRegistrationService userRegistrationService) {
    this.userService = userService;
    this.userRegistrationService = userRegistrationService;
  }

  public ResponseEntity<UserRegistrationDto> register(UserRegistrationDto dto) {
    Optional<User> user = this.userService.findUserByUsername(dto.getUsername());

    if (user.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(this.userRegistrationService.saveDto(dto));
  }


}
