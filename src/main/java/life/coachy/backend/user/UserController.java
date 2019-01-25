package life.coachy.backend.user;

import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.security.AuthenticatedUser;
import life.coachy.backend.util.security.RequiresAuthenticated;
import life.coachy.backend.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
class UserController extends AbstractCrudController<User, ObjectId, UserCrudDto, UserRegistrationDto> {

  private final UserService userService;
  private final SmartValidator smartValidator;

  @Autowired
  protected UserController(UserService userService,
      @Qualifier("localValidatorFactoryBean") SmartValidator smartValidator) {
    super(userService);
    this.userService = userService;
    this.smartValidator = smartValidator;
  }

  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  protected ResponseEntity<?> update(@RequestBody UserCrudDto dto, @PathVariable ObjectId id, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> {
      if (this.userService.existsByEmail(dto.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }

      return super.update(dto, id, result);
    });
  }


  @Override
  protected ResponseEntity<UserCrudDto> partialUpdate(@RequestBody UserCrudDto dto, @PathVariable ObjectId id) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.partialUpdate(dto, id);
  }

}
