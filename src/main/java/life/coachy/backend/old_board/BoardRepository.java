package life.coachy.backend.old_board;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BoardRepository extends MongoRepository<Board, ObjectId> {

  Optional<Board> findByName(String name);

}
