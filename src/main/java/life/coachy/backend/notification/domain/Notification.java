package life.coachy.backend.notification.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.NOTIFICATIONS)
class Notification {

  @Id private ObjectId identifier;
  private ObjectId senderId;
  private String senderName;
  private String senderAvatar;
  private ObjectId recipientId;
  private String content;
  private NotificationType type;
  @CreatedDate private LocalDateTime createdAt;
  private boolean read;

  Notification() {}

  Notification(NotificationBuilder builder) {
    this.senderId = builder.senderId;
    this.senderName = builder.senderName;
    this.senderAvatar = builder.senderAvatar;
    this.recipientId = builder.recipientId;
    this.content = builder.content;
    this.type = builder.type;
    this.createdAt = builder.createdAt;
    this.read = false;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

}
