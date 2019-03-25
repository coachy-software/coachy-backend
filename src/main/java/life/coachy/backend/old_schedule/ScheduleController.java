package life.coachy.backend.old_schedule;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import life.coachy.backend.old_schedule.dto.ScheduleDto;
import life.coachy.backend.old_schedule.dto.ScheduleUpdateDto;
import life.coachy.backend.infrastructure.util.validation.ValidationUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/schedules")
@RestController
class ScheduleController extends AbstractCrudController<Schedule, ObjectId, ScheduleUpdateDto, ScheduleDto> {

  private static final String SPEL_EXPRESSION = "isAuthenticated()";

  private final SmartValidator smartValidator;
  private final ScheduleCrudService service;
//  private final UserFacade userFacade;

  @Autowired
  protected ScheduleController(@Qualifier("localValidatorFactoryBean") SmartValidator smartValidator,
      ScheduleCrudService service) {
    super(service);
    this.smartValidator = smartValidator;
    this.service = service;
//    this.userFacade = userFacade;
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @ApiOperation("Displays all schedules")
  @GetMapping
  public ResponseEntity<?> readAll(
      @ApiParam("QueryDSL") @QuerydslPredicate(root = Schedule.class, bindings = ScheduleQueryBinder.class) Predicate predicate,
      Pageable pageable) {
    return PredicateResponseFactory.obtainResponse(predicate, pageable, this.service);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<Schedule> read(@PathVariable ObjectId id) {
//    if (!this.userFacade.hasPermission("old_schedule." + id + ".read")) {
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }

    return super.read(id);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> update(@RequestBody ScheduleUpdateDto dto, @PathVariable ObjectId id,
      BindingResult result) {
//    if (!this.userFacade.hasPermission("old_schedule." + id + ".update")) {
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
//
    return ValidationUtil.validate(dto, this.smartValidator, result, () -> super.update(dto, id, result));
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<?> partialUpdate(@RequestBody ScheduleUpdateDto dto, @PathVariable ObjectId id) {
//    if (!this.userFacade.hasPermission("old_schedule." + id + ".update")) {
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }

    return super.partialUpdate(dto, id);
  }

  @PreAuthorize(SPEL_EXPRESSION)
  @Override
  protected ResponseEntity<Schedule> remove(@PathVariable ObjectId id) {
//    if (!this.userFacade.hasPermission("old_schedule." + id + ".delete")) {
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
//
    return super.remove(id);
  }

}
