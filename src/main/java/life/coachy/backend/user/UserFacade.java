package life.coachy.backend.user;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import life.coachy.backend.email.EmailNotFoundException;
import life.coachy.backend.user.dto.UserRegistrationDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    return ResponseEntity.status(HttpStatus.CREATED).body(this.userRegistrationService.saveDto(dto));
  }

  public boolean exists(String email) {
    return this.userCrudService.existsByEmail(email);
  }

  public void resetPassword(String email, String newPassword) {
    this.userCrudService.findByEmail(email)
        .map((user) -> this.userCrudService.savePassword(user, newPassword))
        .orElseThrow(EmailNotFoundException::new);
  }

  public boolean hasPermission(String permission) {
    User userPrincipal = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    return userPrincipal.getPermissions().contains(permission);
  }

  public boolean hasPermissions(String... permissions) {
    Map<String, Boolean> permissionsMap = new ConcurrentHashMap<>();

    for (String permission : permissions) {
      permissionsMap.putIfAbsent(permission, this.hasPermission(permission));
    }

    return !permissionsMap.values().contains(false);
  }

  public void addPermissions(String... permissions) {
    User userPrincipal = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    this.addPermissions(userPrincipal);
  }

  public void addPermissions(ObjectId identifier, String... permissions) {
    this.addPermissions(this.userCrudService.findById(identifier).orElse(null), permissions);
  }

  public void addPermissions(User user, String... permissions) {
    for (String permission : permissions) {
      user.addPermission(permission);
    }
  }

}
