package life.coachy.backend.schedule.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.schedule.ScheduleMapper;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = ScheduleMapper.class, entityName = "Schedule")
public class ScheduleUpdateDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private boolean active;
  @NotNull(message = "{notNull}")
  private List<ScheduleDayDto> days;

  ScheduleUpdateDto(ScheduleUpdateDtoBuilder builder) {
    this.name = builder.name;
    this.active = builder.active;
    this.days = builder.days;
  }

  ScheduleUpdateDto() {}

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

}
