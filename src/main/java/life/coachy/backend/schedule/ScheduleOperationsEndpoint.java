package life.coachy.backend.schedule;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermission;
import life.coachy.backend.schedule.domain.ScheduleFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.SCHEDULES)
class ScheduleOperationsEndpoint {

  private final ScheduleFacade scheduleFacade;

  @Autowired
  ScheduleOperationsEndpoint(ScheduleFacade scheduleFacade) {
    this.scheduleFacade = scheduleFacade;
  }

  @ApiOperation("Accepts the schedule")
  @RequiresPermission("schedule.{scheduleId}.accept")
  @RequiresAuthenticated
  @PostMapping("{scheduleId}/accept")
  ResponseEntity<?> accept(@ApiParam("Schedule identifier") @PathVariable ObjectId scheduleId, @RequestBody Map<String, String> payload) {
    if (payload.get("token") == null) {
      return ResponseEntity.badRequest().build();
    }

    this.scheduleFacade.acceptSchedule(scheduleId, payload.get("token"));
    return ResponseEntity.ok().build();
  }

  @ApiOperation("Rejects the schedule")
  @RequiresPermission("schedule.{scheduleId}.accept")
  @RequiresAuthenticated
  @PostMapping("{scheduleId}/reject")
  ResponseEntity<?> reject(@ApiParam("Schedule identifier") @PathVariable ObjectId scheduleId, @RequestBody Map<String, String> payload) {
    if (payload.get("token") == null) {
      return ResponseEntity.badRequest().build();
    }

    this.scheduleFacade.rejectScheduleRequest(scheduleId, payload.get("token"));
    return ResponseEntity.ok().build();
  }

}
