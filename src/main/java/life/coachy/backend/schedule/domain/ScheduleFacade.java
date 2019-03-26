package life.coachy.backend.schedule.domain;

import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import org.bson.types.ObjectId;

public class ScheduleFacade {

  private final ScheduleService service;
  private final ScheduleCreator creator;

  public ScheduleFacade(ScheduleService service, ScheduleCreator creator) {
    this.service = service;
    this.creator = creator;
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

  public void create(ScheduleCreateCommandDto dto) {
    this.service.save(this.creator.from(dto));
  }

}
