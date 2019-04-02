package life.coachy.backend.schedule.domain.dto;

import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.schedule.day.ScheduleDayDto;

public final class ScheduleUpdateEntireEntityCommandDtoBuilder implements Buildable<ScheduleUpdateEntireEntityCommandDto> {

  String name;
  boolean active;
  String note;
  Set<ScheduleDayDto> days;

  private ScheduleUpdateEntireEntityCommandDtoBuilder() {}

  public static ScheduleUpdateEntireEntityCommandDtoBuilder create() {
    return new ScheduleUpdateEntireEntityCommandDtoBuilder();
  }

  public ScheduleUpdateEntireEntityCommandDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleUpdateEntireEntityCommandDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleUpdateEntireEntityCommandDtoBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleUpdateEntireEntityCommandDtoBuilder withDays(Set<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public ScheduleUpdateEntireEntityCommandDto build() {
    return new ScheduleUpdateEntireEntityCommandDto(this);
  }

}
