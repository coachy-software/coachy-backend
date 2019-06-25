package life.coachy.backend.profile.recommendation.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.RECOMMENDATIONS)
public class RecommendationQueryDto implements QueryDtoMarker {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId id;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId profileUserId;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId from;
  private String content;
  private int rating;
  private boolean visible;
  @JsonSerialize(using = ToStringSerializer.class) private LocalDateTime createdAt;
  @JsonSerialize(using = ToStringSerializer.class) private LocalDateTime lastModifiedDate;

  public ObjectId getId() {
    return this.id;
  }

  public ObjectId getProfileUserId() {
    return this.profileUserId;
  }

  public ObjectId getFrom() {
    return this.from;
  }

  public String getContent() {
    return this.content;
  }

  public int getRating() {
    return this.rating;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getLastModifiedDate() {
    return this.lastModifiedDate;
  }

}
