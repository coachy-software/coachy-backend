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

  Notification() {}

  Notification(NotificationBuilder builder) {
    this.identifier = builder.identifier;
    this.senderId = builder.senderId;
    this.senderName = builder.senderName;
    this.senderAvatar = builder.senderAvatar;
    this.recipientId = builder.recipientId;
    this.content = builder.content;
    this.type = builder.type;
  }

}
