package life.coachy.backend.exercise.template;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface ExerciseTemplateRepository extends MongoRepository<ExerciseTemplate, ObjectId>,
    QuerydslPredicateExecutor<ExerciseTemplate> {

  Optional<ExerciseTemplate> findByName(String name);

}
