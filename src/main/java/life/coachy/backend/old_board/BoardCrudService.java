package life.coachy.backend.old_board;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.old_board.dto.BoardDtoMapperFactory;
import life.coachy.backend.infrastructure.util.dto.AbstractDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class BoardCrudService implements CrudOperationsService<Board, ObjectId> {

  private final BoardRepository boardRepository;
  private final BoardDtoMapperFactory mapperFactory;
//  private final UserFacade userFacade;

  @Autowired
  public BoardCrudService(BoardRepository boardRepository, BoardDtoMapperFactory mapperFactory) {
    this.boardRepository = boardRepository;
    this.mapperFactory = mapperFactory;
//    this.userFacade = userFacade;
  }

  @Override
  public Optional<Board> findByName(String name) {
    return this.boardRepository.findByName(name);
  }

  @Override
  public Optional<Board> findById(ObjectId objectId) {
    return this.boardRepository.findById(objectId);
  }

  @Override
  public List<Board> findAll() {
    return this.boardRepository.findAll();
  }

  @Override
  public Board save(Board entity) {
    return this.boardRepository.save(entity);
  }

  @Override
  public <S extends AbstractDto> Board save(S dto) {
    Board board = this.boardRepository.save(this.mapperFactory.obtainEntity(dto));

    String[] permissions = {
        "old_board." + board.getIdentifier() + ".read",
        "old_board." + board.getIdentifier() + ".update",
        "old_board." + board.getIdentifier() + ".delete"
    };

//    this.userFacade.addPermissions(old_board.getOwner().getIdentifier(), permissions);
    return board;
  }

  @Override
  public void deleteById(ObjectId objectId) {
    Board board = this.findById(objectId).get();
//    UserDto owner = old_board.getOwner();
//
//    this.userFacade.removePermissions(owner.getIdentifier(), old_board.getIdentifier());
    this.boardRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.boardRepository.existsById(objectId);
  }

  // MAJOR! can be undefined as long as i don't want to add searching

  @Override
  public <S> List<S> findAll(Predicate predicate) {
    return null;
  }

  @Override
  public <S> Page<S> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public <S> Page<S> findAll(Predicate predicate, Pageable pageable) {
    return null;
  }

}
