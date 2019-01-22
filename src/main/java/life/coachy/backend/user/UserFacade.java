package life.coachy.backend.user;

import java.util.Optional;
import life.coachy.backend.email.EmailNotFoundException;
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

  public ResponseEntity<?> register(UserRegistrationDto dto) {
    Optional<User> user = this.userService.findByName(dto.getUsername());
    if (user.isPresent() || this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(this.userRegistrationService.saveDto(dto).toEntity());
  }

  public User show(String username) {
    return this.userService.findByName(username).orElse(null);
  }

  public boolean exists(String email) {
    return this.userService.existsByEmail(email);
  }

  public User resetPassword(String email, String newPassword) {
    return this.userService.findByEmail(email)
        .map((user) -> this.userService.savePassword(user, newPassword))
        .orElseThrow(EmailNotFoundException::new);
  }

}
