package life.coachy.backend.exercise;

import life.coachy.backend.util.AbstractCrudController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

class ExerciseTemplateController extends AbstractCrudController<ExerciseTemplate, ObjectId,
    ExerciseTemplateUpdateDto, ExerciseTemplateUpdateDto> {

  ExerciseTemplateController(@Autowired ExerciseTemplateCrudService service) {
    super(service);
  }

}
