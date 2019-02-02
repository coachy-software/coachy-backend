package life.coachy.backend.user;

import java.util.Optional;
import life.coachy.backend.email.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

  private final UserCrudService userCrudService;
  private final UserRegistrationService userRegistrationService;

  @Autowired
  public UserFacade(UserCrudService userCrudService, UserRegistrationService userRegistrationService) {
    this.userCrudService = userCrudService;
    this.userRegistrationService = userRegistrationService;
  }

  public ResponseEntity<?> register(UserRegistrationDto dto) {
    Optional<User> user = this.userCrudService.findByName(dto.getUsername());
    if (user.isPresent() || this.userCrudService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

//    return ResponseEntity.status(HttpStatus.CREATED).body(this.userRegistrationService.saveDto(dto).toEntity()); TODO
    return null;
  }

  public User show(String username) {
    return this.userCrudService.findByName(username).orElse(null);
  }

  public boolean exists(String email) {
    return this.userCrudService.existsByEmail(email);
  }

  public User resetPassword(String email, String newPassword) {
    return this.userCrudService.findByEmail(email)
        .map((user) -> this.userCrudService.savePassword(user, newPassword))
        .orElseThrow(EmailNotFoundException::new);
  }

}
