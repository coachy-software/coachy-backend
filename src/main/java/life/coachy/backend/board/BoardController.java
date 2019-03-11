package life.coachy.backend.board;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import life.coachy.backend.user.dto.UserUpdateDto;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.PredicateResponseFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/boards")
@RestController
class BoardController extends AbstractCrudController<Board, ObjectId, UserUpdateDto, UserUpdateDto> {

  private final BoardCrudService service;

  protected BoardController(BoardCrudService service) {
    super(service);
    this.service = service;
  }

  @ApiOperation("Displays all users")
  @GetMapping
  public ResponseEntity<?> readAll() {
    return ResponseEntity.ok(this.service.findAll());
  }

}
