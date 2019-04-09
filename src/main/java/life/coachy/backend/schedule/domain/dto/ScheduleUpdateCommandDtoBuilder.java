package life.coachy.backend.schedule.domain.dto;

import java.util.LinkedHashSet;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.schedule.day.ScheduleDayDto;

public final class ScheduleUpdateCommandDtoBuilder implements Buildable<ScheduleUpdateCommandDto> {

  String name;
  boolean active;
  String note;
  LinkedHashSet<ScheduleDayDto> days;

  private ScheduleUpdateCommandDtoBuilder() {}

  public static ScheduleUpdateCommandDtoBuilder create() {
    return new ScheduleUpdateCommandDtoBuilder();
  }

  public ScheduleUpdateCommandDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleUpdateCommandDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleUpdateCommandDtoBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleUpdateCommandDtoBuilder withDays(LinkedHashSet<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public ScheduleUpdateCommandDto build() {
    return new ScheduleUpdateCommandDto(this);
  }

}
