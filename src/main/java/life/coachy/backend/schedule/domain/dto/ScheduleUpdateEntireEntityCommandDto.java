package life.coachy.backend.schedule.domain.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;

public class ScheduleUpdateEntireEntityCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private boolean active;
  @NotNull private String note;
  @NotNull private List<ScheduleDayDto> days;

  public String getName() {
    return this.name;
  }

  public boolean isActive() {
    return this.active;
  }

  public String getNote() {
    return this.note;
  }

  public List<ScheduleDayDto> getDays() {
    return this.days;
  }

}
