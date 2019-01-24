package life.coachy.backend.exercise;

import life.coachy.backend.util.AbstractCrudController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/exercises")
@RestController
class ExerciseTemplateController extends AbstractCrudController<ExerciseTemplate, ObjectId,
    ExerciseTemplateUpdateDto, ExerciseTemplateUpdateDto> {

  private static final String SPEL_EXPRESSION = "isAuthenticated() && hasAuthority('ADMIN')";

  ExerciseTemplateController(@Autowired ExerciseTemplateCrudService service) {
    super(service);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> update(ExerciseTemplateUpdateDto dto, ObjectId objectId, BindingResult result) {
    return super.update(dto, objectId, result);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ExerciseTemplateUpdateDto> partialUpdate(ExerciseTemplateUpdateDto dto, ObjectId objectId) {
    return super.partialUpdate(dto, objectId);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ExerciseTemplate> remove(ObjectId objectId) {
    return super.remove(objectId);
  }

}
