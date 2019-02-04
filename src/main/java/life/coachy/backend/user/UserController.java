package life.coachy.backend.user;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.PredicateResponseFactory;
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

  @ApiOperation("Displays current user info")
  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> update(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> {
      if (this.userCrudService.existsByEmail(dto.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }

      return super.update(dto, id, result);
    });
  }


  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<UserUpdateDto> partialUpdate(@RequestBody UserUpdateDto dto, @PathVariable ObjectId id) {
    if (this.userCrudService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.partialUpdate(dto, id);
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<User> remove(@PathVariable ObjectId id) {
    return super.remove(id);
  }

}
