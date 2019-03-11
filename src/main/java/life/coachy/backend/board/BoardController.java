package life.coachy.backend.board;

import io.swagger.annotations.ApiOperation;
import life.coachy.backend.user.dto.UserUpdateDto;
import life.coachy.backend.util.AbstractCrudController;
import org.bson.types.ObjectId;
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
