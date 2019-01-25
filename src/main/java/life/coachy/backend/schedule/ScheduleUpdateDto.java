package life.coachy.backend.schedule;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.util.AbstractDto;

public class ScheduleUpdateDto extends AbstractDto<Schedule> {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private boolean active;
  @NotNull(message = "{notNull}")
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
