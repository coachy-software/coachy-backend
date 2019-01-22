package life.coachy.backend.user;

import javax.validation.Valid;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.security.AuthenticatedUser;
import life.coachy.backend.util.security.RequiresAuthenticated;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
class UserController extends AbstractCrudController<User, ObjectId, UserCrudDto, UserRegistrationDto> {

  private final UserService userService;

  @Autowired
  protected UserController(UserService userService) {
    super(userService);
    this.userService = userService;
  }

  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  @Valid
  protected ResponseEntity<?> update(@RequestBody UserCrudDto dto, @PathVariable ObjectId id,
      BindingResult result) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.update(dto, id, result);
  }


  @Override
  protected ResponseEntity<UserCrudDto> partialUpdate(@RequestBody UserCrudDto dto, @PathVariable ObjectId id) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.partialUpdate(dto, id);
  }

}
