package life.coachy.backend.schedule.domain.dto;

import java.util.Set;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import org.bson.types.ObjectId;

public class ScheduleCreateCommandDto implements CommandDtoMarker {

  private String name;
  private ObjectId creator;
  private ObjectId charge;
  private String note;
  private boolean active;
  private Set<ScheduleDayDto> days;

  public String getName() {
    return this.name;
  }

  public ObjectId getCreator() {
    return this.creator;
  }

  public ObjectId getCharge() {
    return this.charge;
  }

  public String getNote() {
    return this.note;
  }

  public boolean isActive() {
    return this.active;
  }

  public Set<ScheduleDayDto> getDays() {
    return this.days;
  }

}
