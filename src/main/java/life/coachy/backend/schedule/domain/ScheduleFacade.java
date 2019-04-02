package life.coachy.backend.schedule.domain;

import java.net.URI;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ScheduleFacade {

  private final ScheduleService service;
  private final ScheduleCreator creator;
  private final UserFacade userFacade;

  public ScheduleFacade(ScheduleService service, ScheduleCreator creator, UserFacade userFacade) {
    this.service = service;
    this.creator = creator;
    this.userFacade = userFacade;
  }

  public void delete(ObjectId id) {
    this.service.delete(id);
  }

  public void update(ObjectId id, ScheduleUpdateEntireEntityCommandDto dto) {
    this.service.convertPropertiesToMapAndSave(id, dto);
  }

  public ScheduleQueryDto fetchOne(ObjectId id) {
    return this.service.fetchOne(id);
  }

  public URI create(ScheduleCreateCommandDto dto) {
    Schedule schedule = this.service.save(this.creator.from(dto));
    this.service.givePermissions(this.userFacade, schedule, dto);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.SCHEDULES + "/{id}").buildAndExpand(schedule.identifier).toUri();
  }

}
