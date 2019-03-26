package life.coachy.backend.board.domain;

import life.coachy.backend.board.domain.dto.BoardUpdateEntireEntityCommandDto;
import life.coachy.backend.board.domain.exception.BoardNotFoundException;
import life.coachy.backend.board.query.BoardQueryDto;
import life.coachy.backend.board.query.BoardQueryDtoRepository;
import life.coachy.backend.infrastructure.converter.PropertiesToMapConverter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BoardService {

  private final BoardRepository repository;
  private final BoardQueryDtoRepository queryDtoRepository;
  private final PropertiesToMapConverter propertiesToMapConverter;

  @Autowired
  public BoardService(BoardRepository repository, BoardQueryDtoRepository queryDtoRepository, PropertiesToMapConverter propertiesToMapConverter) {
    this.repository = repository;
    this.queryDtoRepository = queryDtoRepository;
    this.propertiesToMapConverter = propertiesToMapConverter;
  }

  void save(Board board) {
    this.repository.save(board);
  }

  void convertPropertiesToMapAndSave(ObjectId id, BoardUpdateEntireEntityCommandDto dto) {
    this.checkIfExists(id, () -> this.repository.updateEntireEntity(id, this.propertiesToMapConverter.convert(dto)));
  }

  BoardQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(BoardNotFoundException::new);
  }

  private void checkIfExists(ObjectId id, Runnable runnable) {
    if (!this.queryDtoRepository.existsByIdentifier(id)) {
      throw new BoardNotFoundException();
    }
    runnable.run();
  }

}
