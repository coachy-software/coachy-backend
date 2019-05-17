package life.coachy.backend.board;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.board.domain.BoardFacade;
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateCommandDto;
import life.coachy.backend.board.query.BoardQueryDto;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.BOARDS)
class BoardEndpoint {

  private final BoardFacade boardFacade;

  @Autowired
  public BoardEndpoint(BoardFacade boardFacade) {
    this.boardFacade = boardFacade;
  }

  @RequiresPermissions("board.{id}.read")
  @RequiresAuthenticated
  @ApiOperation("Displays specified board query data transfer object by identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Board not found"),
      @ApiResponse(code = 201, message = "Successfully displayed")
  })
  @GetMapping("{id}")
  public ResponseEntity<BoardQueryDto> fetchOne(@PathVariable @ApiParam("Board's id") ObjectId id) {
    return ResponseEntity.ok(this.boardFacade.fetchOne(id));
  }

  @RequiresPermissions("board.{id}.update")
  @RequiresAuthenticated
  @ApiOperation("Updates board by identifier")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error"),
      @ApiResponse(code = 404, message = "Board not found"),
      @ApiResponse(code = 204, message = "Successfully updated")
  })
  @PutMapping("{id}")
  public ResponseEntity<BoardQueryDto> update(@PathVariable @ApiParam("Board's id") ObjectId id, @Valid @RequestBody BoardUpdateCommandDto dto) {
    this.boardFacade.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @RequiresAuthenticated
  @ApiOperation("Creates board")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error"),
      @ApiResponse(code = 204, message = "Successfully created")
  })
  @PostMapping
  public ResponseEntity<BoardQueryDto> create(@Valid @RequestBody BoardCreateCommandDto dto) {
    return ResponseEntity.created(this.boardFacade.create(dto)).build();
  }

}
