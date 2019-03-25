package life.coachy.backend.old_schedule.dto;

import java.util.List;
import life.coachy.backend.old_schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.infrastructure.util.Buildable;

public final class ScheduleUpdateDtoBuilder implements Buildable<ScheduleUpdateDto> {

  String name;
  boolean active;
  String note;
  List<ScheduleDayDto> days;

  private ScheduleUpdateDtoBuilder() {}

  public static ScheduleUpdateDtoBuilder createBuilder() {
    return new ScheduleUpdateDtoBuilder();
  }

  public ScheduleUpdateDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleUpdateDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleUpdateDtoBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleUpdateDtoBuilder withDays(List<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public ScheduleUpdateDto build() {
    return new ScheduleUpdateDto(this);
  }

}
