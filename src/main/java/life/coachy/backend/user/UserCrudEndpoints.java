package life.coachy.backend.user;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import life.coachy.backend.user.query.UserQueryBinder;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import life.coachy.backend.user.query.UserQueryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("new_users")
class UserCrudEndpoints {

  private final UserFacade facade;
  private final UserQueryService service;
  private final UserQueryDtoRepository repository;
  private final QueryOperationsFactory queryOperationsFactory;

  @Autowired
  public UserCrudEndpoints(UserFacade facade, UserQueryService service, UserQueryDtoRepository repository, QueryOperationsFactory queryOperationsFactory) {
    this.facade = facade;
    this.service = service;
    this.repository = repository;
    this.queryOperationsFactory = queryOperationsFactory;
  }

  @ApiOperation("Displays users that matches criteria, only if criteria present, otherwise displays all users")
  @GetMapping
  public ResponseEntity<List<UserQueryDto>> fetchAll(@QuerydslPredicate(bindings = UserQueryBinder.class) Predicate predicate, Pageable pageable) {
    return ResponseEntity.ok(this.queryOperationsFactory.obtainOperation(predicate, pageable, this.repository));
  }

  @ApiOperation("Displays specified user query data transfer object")
  @ApiResponses({
      @ApiResponse(code = 404, message = "User not found"),
      @ApiResponse(code = 200, message = "Successfully displayed")
  })
  @GetMapping("{id}")
  public ResponseEntity<UserQueryDto> fetchOne(@PathVariable ObjectId id) {
    return ResponseEntity.ok(this.service.fetchOne(id));
  }

  @ApiOperation("Creates an user")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Successfully created"),
      @ApiResponse(code = 400, message = "Validation error"),
      @ApiResponse(code = 409, message = "User with that email/username already exists")
  })
  @PostMapping
  public ResponseEntity<UserRegisterCommandDto> create(@Valid @RequestBody UserRegisterCommandDto dto) {
    this.facade.register(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
