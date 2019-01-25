package life.coachy.backend.schedule;

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

@RequestMapping("/api/schedules")
@RestController
class ScheduleController extends AbstractCrudController<Schedule, ObjectId, ScheduleUpdateDto, ScheduleDto> {

  private static final String SPEL_EXPRESSION = "isAuthenticated()";
  private final SmartValidator smartValidator;

  @Autowired
  protected ScheduleController(@Qualifier("localValidatorFactoryBean") SmartValidator smartValidator,
      ScheduleCrudService service) {
    super(service);
    this.smartValidator = smartValidator;
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> update(@RequestBody ScheduleUpdateDto dto, @PathVariable ObjectId id,
      BindingResult result) {
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.update(dto, id, result));
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ScheduleUpdateDto> partialUpdate(@RequestBody ScheduleUpdateDto dto,
      @PathVariable ObjectId id) {
    return super.partialUpdate(dto, id);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<Schedule> remove(@PathVariable ObjectId id) {
    return super.remove(id);
  }

}
