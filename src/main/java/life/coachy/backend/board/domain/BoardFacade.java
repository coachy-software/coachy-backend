package life.coachy.backend.board.domain;

import java.net.URI;
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateEntireEntityCommandDto;
import life.coachy.backend.board.query.BoardQueryDto;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BoardFacade {

  private final BoardService boardService;
  private final BoardCreator boardCreator;
  private final UserFacade userFacade;

  public BoardFacade(BoardService boardService, BoardCreator boardCreator, UserFacade userFacade) {
    this.boardService = boardService;
    this.boardCreator = boardCreator;
    this.userFacade = userFacade;
  }

  public URI create(BoardCreateCommandDto dto) {
    Board board = this.boardService.save(this.boardCreator.from(dto));
    this.boardService.givePermissions(this.userFacade, board, dto);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.BOARDS + "/{id}").buildAndExpand(board.identifier).toUri();
  }

  public void update(ObjectId id, BoardUpdateEntireEntityCommandDto dto) {
    this.boardService.convertPropertiesToMapAndSave(id, dto);
  }

  public BoardQueryDto fetchOne(ObjectId id) {
    return this.boardService.fetchOne(id);
  }

}
