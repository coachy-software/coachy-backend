package life.coachy.backend.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.schedule.ScheduleMapper;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.old_user.dto.UserDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = ScheduleMapper.class, entityName = "Schedule")
public class ScheduleDto extends AbstractDto {

  private ObjectId identifier;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private UserDto creator;
  @NotNull(message = "{notNull}")
  private UserDto charge;
  private String note;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean active;
  private List<ScheduleDayDto> days;

  ScheduleDto(ScheduleDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.creator = builder.creator;
    this.charge = builder.charge;
    this.note = builder.note;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
    this.days = builder.days;
  }

  ScheduleDto() {}

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public UserDto getCreator() {
    return this.creator;
  }

  public UserDto getCharge() {
    return this.charge;
  }

  public String getNote() {
    return this.note;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
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

}
