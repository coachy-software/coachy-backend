package life.coachy.backend.old_user;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import java.util.function.Supplier;
import life.coachy.backend.old_user.dto.UserRegistrationDto;
import life.coachy.backend.old_user.dto.UserUpdateDto;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.PredicateResponseFactory;
import life.coachy.backend.util.security.AuthenticatedUser;
import life.coachy.backend.util.security.RequiresAdmin;
import life.coachy.backend.util.security.RequiresAuthenticated;
import life.coachy.backend.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  private static final String SPEL_EXPRESSION = "(isAuthenticated() && principal.user.identifier.equals(#id)) || hasAuthority('ADMIN')";
  private final UserCrudService userCrudService;
  private final SmartValidator smartValidator;

  @Autowired
  protected UserController(UserCrudService userCrudService,
      @Qualifier("localValidatorFactoryBean") SmartValidator smartValidator) {
    super(userCrudService);
    this.userCrudService = userCrudService;
    this.smartValidator = smartValidator;
  }

  @ApiOperation("Displays all users")
  @GetMapping
  public ResponseEntity<?> readAll(
      @ApiParam("QueryDSL") @QuerydslPredicate(root = User.class, bindings = UserQueryBinder.class) Predicate predicate,
      Pageable pageable) {
    return PredicateResponseFactory.obtainResponse(predicate, pageable, this.userCrudService);
  }

  @ApiOperation("Displays current old_user info")
  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  @RequiresAdmin
  protected ResponseEntity<?> create(@RequestBody UserRegistrationDto dto, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.create(dto, result));
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> update(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> this.checkIfNotExist(id, dto, result));
  }


  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> partialUpdate(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id) {
    return this.checkIfNotExist(id, dto);
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<User> remove(@PathVariable ObjectId id) {
    return super.remove(id);
  }

  private ResponseEntity<?> checkIfNotExist(ObjectId id, UserUpdateDto dto) {
    return this.checkIfNotExist(id, dto, () -> super.partialUpdate(dto, id));
  }

  private ResponseEntity<?> checkIfNotExist(ObjectId id, UserUpdateDto dto, BindingResult result) {
    return this.checkIfNotExist(id, dto, () -> super.update(dto, id, result));
  }

  private ResponseEntity<?> checkIfNotExist(ObjectId id, UserUpdateDto dto, Supplier<ResponseEntity<?>> supplier) {
    Optional<User> optionalUser = this.userCrudService.findById(id);

    if (optionalUser.isPresent()) {
      String email = optionalUser.get().getEmail();
      String username = optionalUser.get().getUsername();

      if (email.equalsIgnoreCase(dto.getEmail()) || username.equalsIgnoreCase(dto.getUsername())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
    }

    return supplier.get();
  }

}
