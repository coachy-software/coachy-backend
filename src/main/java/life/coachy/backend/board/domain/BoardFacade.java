package life.coachy.backend.board.domain;

import java.net.URI;
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateEntireEntityCommandDto;
import life.coachy.backend.board.query.BoardQueryDto;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BoardFacade {

  private final BoardService boardService;
  private final BoardCreator boardCreator;

  public BoardFacade(BoardService boardService, BoardCreator boardCreator) {
    this.boardService = boardService;
    this.boardCreator = boardCreator;
  }

  public URI create(BoardCreateCommandDto dto) {
    Board board = this.boardService.save(this.boardCreator.from(dto));
    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.BOARDS + "/{id}").buildAndExpand(board.identifier).toUri();
  }

  public void update(ObjectId id, BoardUpdateEntireEntityCommandDto dto) {
    this.boardService.convertPropertiesToMapAndSave(id, dto);
  }

  public BoardQueryDto fetchOne(ObjectId id) {
    return this.boardService.fetchOne(id);
  }

}
