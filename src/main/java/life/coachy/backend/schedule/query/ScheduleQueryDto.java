package life.coachy.backend.schedule.query;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.Set;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto.View;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.SCHEDULES)
@JsonView(View.Global.class)
public class ScheduleQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String name;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId creator;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId charge;
  @JsonView(View.Default.class) private String note;
  @JsonSerialize(using = ToStringSerializer.class) @CreatedDate private LocalDateTime createdAt;
  @JsonSerialize(using = ToStringSerializer.class) @LastModifiedDate private LocalDateTime updatedAt;
  private boolean active;
  @JsonView(View.Default.class) private Set<ScheduleDayDto> days;
  private boolean accepted;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public ObjectId getCreator() {
    return this.creator;
  }

  public ObjectId getCharge() {
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

  public Set<ScheduleDayDto> getDays() {
    return this.days;
  }

  public boolean isAccepted() {
    return this.accepted;
  }

  public static class View {
    public interface Global {}
    public interface Default {}
  }

}
