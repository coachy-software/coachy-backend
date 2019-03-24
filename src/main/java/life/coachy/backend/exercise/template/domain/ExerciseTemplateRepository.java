package life.coachy.backend.exercise.template.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface ExerciseTemplateRepository extends CommandRepository<ExerciseTemplate, ObjectId> {

}
