package life.coachy.backend.user;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.security.AuthenticatedUser;
import life.coachy.backend.util.security.RequiresAuthenticated;
import life.coachy.backend.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
class UserController extends AbstractCrudController<User, ObjectId, UserUpdateDto, UserRegistrationDto> {

  private final UserService userService;
  private final SmartValidator smartValidator;

  @Autowired
  protected UserController(UserService userService,
      @Qualifier("localValidatorFactoryBean") SmartValidator smartValidator) {
    super(userService);
    this.userService = userService;
    this.smartValidator = smartValidator;
  }

  @ApiOperation("Displays all users")
  @GetMapping
  public ResponseEntity<Iterable<User>> readAll(
      @ApiParam("QueryDSL") @QuerydslPredicate(root = User.class, bindings = UserQueryBinder.class) Predicate predicate,
      Pageable pageable) {

    boolean isPredicatePresent = !(predicate == null);
    boolean isPaginationPresent = pageable.toOptional().isPresent();
    boolean isPagginationAndPredicatePresent = isPredicatePresent && isPaginationPresent;

    if (isPagginationAndPredicatePresent) {
      return ResponseEntity.ok(this.userService.findAll(predicate, pageable));
    } else if (isPaginationPresent) {
      return ResponseEntity.ok(this.userService.findAll(pageable));
    } else {
      return ResponseEntity.ok(this.userService.findAll());
    }
  }

  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  protected ResponseEntity<?> update(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> {
      if (this.userService.existsByEmail(dto.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }

      return super.update(dto, id, result);
    });
  }


  @Override
  protected ResponseEntity<UserUpdateDto> partialUpdate(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.partialUpdate(dto, id);
  }

}
