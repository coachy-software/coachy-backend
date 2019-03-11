package life.coachy.backend.board;

import io.swagger.annotations.ApiOperation;
import life.coachy.backend.board.dto.BoardUpdateDto;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
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

@RequestMapping("/api/boards")
@RestController
class BoardController extends AbstractCrudController<Board, ObjectId, BoardUpdateDto, BoardUpdateDto> {

  private static final String SPEL_EXPRESSION = "isAuthenticated()";

  private final BoardCrudService service;
  private final SmartValidator smartValidator;

  protected BoardController(BoardCrudService service,
      @Qualifier("localValidatorFactoryBean") SmartValidator smartValidator) {
    super(service);
    this.service = service;
    this.smartValidator = smartValidator;
  }

  @ApiOperation("Displays all users")
  @PreAuthorize(SPEL_EXPRESSION)
  @GetMapping
  public ResponseEntity<?> readAll() {
    return ResponseEntity.ok(this.service.findAll());
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<Board> read(@PathVariable ObjectId id) {
    return super.read(id);
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> create(@RequestBody BoardUpdateDto dto, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.create(dto, result));
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> update(@RequestBody BoardUpdateDto dto, @PathVariable ObjectId id, BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.update(dto, id, result));
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<?> partialUpdate(@RequestBody BoardUpdateDto dto, @PathVariable ObjectId id) {
    return super.partialUpdate(dto, id);
  }

  @Override
  @PreAuthorize(SPEL_EXPRESSION)
  protected ResponseEntity<Board> remove(@PathVariable ObjectId id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

}
