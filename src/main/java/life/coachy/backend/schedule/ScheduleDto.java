package life.coachy.backend.schedule;

import java.util.Date;
import java.util.List;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.user.UserDto;
import life.coachy.backend.util.AbstractDto;
import org.bson.types.ObjectId;

public class ScheduleDto extends AbstractDto<Schedule> {

  private ObjectId identifier;
  private String name;
  private UserDto creator;
  private Date createdAt;
  private Date updatedAt;
  private long version;
  private boolean active;
  private List<ScheduleDayDto> trainingDays;

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

  public long getVersion() {
    return this.version;
  }

  public boolean isActive() {
    return this.active;
  }

  public List<ScheduleDayDto> getTrainingDays() {
    return this.trainingDays;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

  @Override
  public Schedule toEntity() {
    return new ScheduleBuilder()
        .withIdentifier(this.identifier)
        .withName(this.name)
        .withCreator(this.creator)
        .withCreatedAt(this.createdAt)
        .withUpdatedAt(this.updatedAt)
        .withVersion(this.version)
        .isActive(this.active)
        .withTrainingDays(this.trainingDays)
        .build();
  }

}
