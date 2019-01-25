package life.coachy.backend.schedule;

import java.util.List;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.util.AbstractDto;

public class ScheduleUpdateDto extends AbstractDto<Schedule> {

  private String name;
  private boolean active;
  private List<ScheduleDayDto> days;

  public String getName() {
    return this.name;
  }

  public boolean isActive() {
    return this.active;
  }

  public List<ScheduleDayDto> getDays() {
    return this.days;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

  @Override
  public Schedule toEntity() {
    return new ScheduleBuilder()
        .withName(this.name)
        .isActive(this.active)
        .withDays(this.days)
        .build();
  }

}
