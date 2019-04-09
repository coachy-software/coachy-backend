package life.coachy.backend.board.domain;

import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.exception.BoardNotFoundException;
import life.coachy.backend.board.query.BoardQueryDto;
import life.coachy.backend.board.query.BoardQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BoardService {

  private final BoardRepository repository;
  private final BoardQueryRepository queryDtoRepository;

  @Autowired
  public BoardService(BoardRepository repository, BoardQueryRepository queryDtoRepository) {
    this.repository = repository;
    this.queryDtoRepository = queryDtoRepository;
  }

  Board save(Board board) {
    return this.repository.save(board);
  }

  void update(ObjectId id, Board board) {
    BoardQueryDto queryDto = this.fetchOne(id);
    board.setIdentifier(queryDto.getIdentifier());
    board.setOwnerId(queryDto.getOwnerId());

    this.repository.save(board);
  }

  BoardQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(BoardNotFoundException::new);
  }

  void givePermissions(UserFacade userFacade, Board board, BoardCreateCommandDto dto) {
    userFacade.givePermissions(dto.getOwnerId(), "board." + board.identifier + ".read", "board." + board.identifier + ".update");
  }

}
