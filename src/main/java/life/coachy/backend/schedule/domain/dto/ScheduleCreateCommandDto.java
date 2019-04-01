package life.coachy.backend.schedule.domain.dto;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import org.bson.types.ObjectId;

public class ScheduleCreateCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private ObjectId creator;
  @NotNull private ObjectId charge;
  @NotNull private String note;
  @NotNull private boolean active;
  @NotNull private Set<ScheduleDayDto> days;

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
