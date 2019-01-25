package life.coachy.backend.schedule;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.user.UserDto;
import life.coachy.backend.util.AbstractDto;
import org.bson.types.ObjectId;

public class ScheduleDto extends AbstractDto<Schedule> {

  private ObjectId identifier;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private UserDto creator;
  private Date createdAt;
  private Date updatedAt;
  @NotNull(message = "{notNull}")
  private boolean active;
  @NotNull(message = "{notNull}")
  private List<ScheduleDayDto> days;

  ScheduleDto() {
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public UserDto getCreator() {
    return this.creator;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
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
        .withCreator(this.creator)
        .withCreatedAt(this.createdAt)
        .withUpdatedAt(this.updatedAt)
        .isActive(this.active)
        .withDays(this.days)
        .build();
  }

}
