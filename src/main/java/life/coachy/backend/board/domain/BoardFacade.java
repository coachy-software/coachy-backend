package life.coachy.backend.board.domain;

import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateEntireEntityCommandDto;
import life.coachy.backend.board.query.BoardQueryDto;
import org.bson.types.ObjectId;

public class BoardFacade {

  private final BoardService boardService;
  private final BoardCreator boardCreator;

  public BoardFacade(BoardService boardService, BoardCreator boardCreator) {
    this.boardService = boardService;
    this.boardCreator = boardCreator;
  }

  public void create(BoardCreateCommandDto dto) {
    this.boardService.save(this.boardCreator.from(dto));
  }

  public void update(ObjectId id, BoardUpdateEntireEntityCommandDto dto) {
    this.boardService.convertPropertiesToMapAndSave(id, dto);
  }

  public BoardQueryDto fetchOne(ObjectId id) {
    return this.boardService.fetchOne(id);
  }

}
