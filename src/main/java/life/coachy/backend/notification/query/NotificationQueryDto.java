package life.coachy.backend.notification.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.NOTIFICATIONS)
public class NotificationQueryDto implements QueryDtoMarker {

  @Id private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId senderId;
  private String senderName;
  private String senderAvatar;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId recipientId;
  private String content;
  private String type;
  @JsonSerialize(using = ToStringSerializer.class) private LocalDateTime createdAt;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getSenderId() {
    return this.senderId;
  }

  public String getSenderName() {
    return this.senderName;
  }

  public String getSenderAvatar() {
    return this.senderAvatar;
  }

  public ObjectId getRecipientId() {
    return this.recipientId;
  }

  public String getContent() {
    return this.content;
  }

  public String getType() {
    return this.type;
  }

}
