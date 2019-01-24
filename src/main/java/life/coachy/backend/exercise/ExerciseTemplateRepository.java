package life.coachy.backend.exercise;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ExerciseTemplateRepository extends MongoRepository<ExerciseTemplate, ObjectId> {

  Optional<ExerciseTemplate> findByName(String name);

}
