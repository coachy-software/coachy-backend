package life.coachy.backend.user;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.old_user.UserQueryBinder;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("new_users")
class UserCrudEndpoints {

  private final UserQueryDtoRepository repository;
  private final QueryOperationsFactory queryOperationsFactory;

  @Autowired
  public UserCrudEndpoints(UserQueryDtoRepository repository, QueryOperationsFactory queryOperationsFactory) {
    this.repository = repository;
    this.queryOperationsFactory = queryOperationsFactory;
  }

  @ApiOperation("Displays users that matches criteria, only if criteria present, otherwise displays all users")
  @GetMapping
  public ResponseEntity<List<UserQueryDto>> fetchAll(@QuerydslPredicate(bindings = UserQueryBinder.class) Predicate predicate, Pageable pageable) {
    return ResponseEntity.ok(this.queryOperationsFactory.obtainOperation(predicate, pageable, this.repository));
  }

}
