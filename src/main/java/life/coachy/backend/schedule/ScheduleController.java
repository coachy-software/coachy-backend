package life.coachy.backend.schedule;

import life.coachy.backend.util.AbstractCrudController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/schedules")
@RestController
class ScheduleController extends AbstractCrudController<Schedule, ObjectId, ScheduleDto, ScheduleDto> {

  // todo
  private static final String SPEL_EXPRESSION = "isAuthenticated() || hasAuthority('ADMIN')";

  protected ScheduleController(@Autowired ScheduleCrudService service) {
    super(service);
  }

  // todo
  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> update(@RequestBody ScheduleDto dto, @PathVariable ObjectId id, BindingResult result) {
    return super.update(dto, id, result);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<ScheduleDto> partialUpdate(@RequestBody ScheduleDto dto, @PathVariable ObjectId id) {
    return super.partialUpdate(dto, id);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<Schedule> remove(@PathVariable ObjectId id) {
    return super.remove(id);
  }

}
