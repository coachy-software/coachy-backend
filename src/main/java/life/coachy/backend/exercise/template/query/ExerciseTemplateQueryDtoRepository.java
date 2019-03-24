package life.coachy.backend.exercise.template.query;

import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;

public interface ExerciseTemplateQueryDtoRepository extends QueryFetchAllRepository<ExerciseTemplateQueryDto, ObjectId>,
    QueryFetchOneRepository<ExerciseTemplateQueryDto, ObjectId> {

}
