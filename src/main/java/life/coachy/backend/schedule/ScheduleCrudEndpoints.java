package life.coachy.backend.schedule;

import com.fasterxml.jackson.annotation.JsonView;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import life.coachy.backend.schedule.domain.ScheduleFacade;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryBinder;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto.View;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.SCHEDULES)
class ScheduleCrudEndpoints {

  private final ScheduleFacade facade;

  @Autowired
  public ScheduleCrudEndpoints(ScheduleFacade facade) {
    this.facade = facade;
  }

  @RequiresAuthenticated
  @ApiOperation("Creates schedule")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error"),
      @ApiResponse(code = 204, message = "Successfully created")
  })
  @PostMapping
  public ResponseEntity<ScheduleQueryDto> create(@Valid @RequestBody ScheduleCreateCommandDto dto) {
    return ResponseEntity.created(this.facade.create(dto)).build();
  }

  @JsonView(View.Global.class)
  @RequiresAuthenticated
  @ApiOperation("Displays schedules that matches criteria, only if criteria present, otherwise displays all schedules")
  @GetMapping
  public ResponseEntity<List<ScheduleQueryDto>> readAll(@QuerydslPredicate(bindings = ScheduleQueryBinder.class) Predicate predicate, Pageable pageable) {
    return ResponseEntity.ok(this.facade.fetchAll(predicate, pageable));
  }

  @RequiresPermissions("schedule.{id}.read")
  @RequiresAuthenticated
  @ApiOperation("Displays specified schedule query data transfer object by identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Schedule not found"),
      @ApiResponse(code = 200, message = "Successfully displayed")
  })
  @GetMapping("{id}")
  public ResponseEntity<ScheduleQueryDto> fetchOne(@PathVariable @ApiParam("User's id") ObjectId id) {
    return ResponseEntity.ok(this.facade.fetchOne(id));
  }

  @RequiresPermissions("schedule.{id}.update")
  @RequiresAuthenticated
  @ApiOperation("Updates schedule by identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Schedule not found"),
      @ApiResponse(code = 204, message = "Successfully updated")
  })
  @PostMapping("{id}")
  public ResponseEntity<ScheduleQueryDto> update(@PathVariable @ApiParam("User's id") ObjectId id, @RequestBody ScheduleUpdateEntireEntityCommandDto dto) {
    this.facade.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @RequiresPermissions("schedule.{id}.delete")
  @RequiresAuthenticated
  @ApiOperation("Deletes schedule by identifier")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Schedule not found"),
      @ApiResponse(code = 204, message = "Successfully deleted")
  })
  @DeleteMapping("{id}")
  public ResponseEntity<ScheduleQueryDto> delete(@PathVariable @ApiParam("User's id") ObjectId id) {
    this.facade.delete(id);
    return ResponseEntity.noContent().build();
  }

}
