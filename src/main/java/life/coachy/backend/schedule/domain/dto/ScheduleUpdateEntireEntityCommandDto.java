package life.coachy.backend.schedule.domain.dto;

import java.util.LinkedHashSet;
import java.util.SortedSet;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.schedule.day.ScheduleDayDto;

public class ScheduleUpdateEntireEntityCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private boolean active;
  @NotNull private String note;
  @NotNull private LinkedHashSet<ScheduleDayDto> days;

  ScheduleUpdateEntireEntityCommandDto() {}

  ScheduleUpdateEntireEntityCommandDto(ScheduleUpdateEntireEntityCommandDtoBuilder builder) {
    this.name = builder.name;
    this.active = builder.active;
    this.note = builder.note;
    this.days = builder.days;
  }

  public String getName() {
    return this.name;
  }

  public boolean isActive() {
    return this.active;
  }

  public String getNote() {
    return this.note;
  }

  public LinkedHashSet<ScheduleDayDto> getDays() {
    return this.days;
  }

}
