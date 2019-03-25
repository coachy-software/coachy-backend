package life.coachy.backend.schedule.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.Set;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonSerialize(using = ScheduleQueryDtoSerializer.class)
@Document(MongoCollections.SCHEDULES)
public class ScheduleQueryDto implements QueryDtoMarker {

  @Id private ObjectId identifier;
  private String name;
  private ObjectId creator;
  private ObjectId charge;
  private String note;
  @CreatedDate private LocalDateTime createdAt;
  @LastModifiedDate private LocalDateTime updatedAt;
  private boolean active;
  private Set<ScheduleDayDto> days;

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

}
