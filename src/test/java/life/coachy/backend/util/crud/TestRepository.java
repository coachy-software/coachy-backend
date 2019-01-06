package life.coachy.backend.util.crud;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends MongoRepository<TestEntity, ObjectId> {

  Optional<TestEntity> findByName(String name);

}