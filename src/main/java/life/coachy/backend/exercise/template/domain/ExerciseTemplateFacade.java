package life.coachy.backend.exercise.template.domain;

import com.google.common.base.Preconditions;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDto;
import org.bson.types.ObjectId;

public class ExerciseTemplateFacade {

  private final ExerciseTemplateCrudService service;

  public ExerciseTemplateFacade(ExerciseTemplateCrudService service) {
    this.service = service;
  }

  public ExerciseTemplateQueryDto fetchOne(ObjectId id) {
    Preconditions.checkNotNull(id, "Exercise template identifier cannot be null!");
    return this.service.fetchOne(id);
  }


}
