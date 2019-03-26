package life.coachy.backend.schedule.domain;

import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import org.bson.types.ObjectId;

public class ScheduleFacade {

  private final ScheduleService service;

  public ScheduleFacade(ScheduleService service) {
    this.service = service;
  }

  public void delete(ObjectId id) { // todo same here
    this.service.delete(id);
  }

  public void update(ObjectId id, ScheduleUpdateEntireEntityCommandDto dto) { // todo check if exists
    this.service.convertPropertiesToMapAndSave(id, dto);
  }

  public ScheduleQueryDto fetchOne(ObjectId id) {
    return this.service.fetchOne(id);
  }

}
