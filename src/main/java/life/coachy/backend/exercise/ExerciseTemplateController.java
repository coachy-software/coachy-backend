package life.coachy.backend.exercise;

import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/exercises")
@RestController
class ExerciseTemplateController extends AbstractCrudController<ExerciseTemplate, ObjectId,
    ExerciseTemplateUpdateDto, ExerciseTemplateUpdateDto> {

  private static final String SPEL_EXPRESSION = "isAuthenticated() && hasAuthority('ADMIN')";
  private final SmartValidator smartValidator;

  @Autowired
  ExerciseTemplateController(ExerciseTemplateCrudService service,
      @Qualifier("localValidatorFactoryBean") SmartValidator smartValidator) {
    super(service);
    this.smartValidator = smartValidator;
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> update(@RequestBody ExerciseTemplateUpdateDto dto, @PathVariable ObjectId id,
      BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.update(dto, id, result));
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ExerciseTemplateUpdateDto> partialUpdate(ExerciseTemplateUpdateDto dto, ObjectId id) {
    return super.partialUpdate(dto, id);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ExerciseTemplate> remove(ObjectId id) {
    return super.remove(id);
  }

}
