package life.coachy.backend.board.query;

import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface BoardQueryRepository extends QueryFetchOneRepository<BoardQueryDto, ObjectId>, Repository<BoardQueryDto, ObjectId> {

  boolean existsByIdentifier(ObjectId id);

}
