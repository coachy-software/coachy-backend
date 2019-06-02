package life.coachy.backend.notification.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class NotificationMessageDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId senderId;
  private String senderName;
  private String senderAvatar;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId recipientId;
  private String content;
  private String type;
  @JsonSerialize(using = ToStringSerializer.class) private LocalDateTime createdAt;

  NotificationMessageDto() {}

  NotificationMessageDto(NotificationMessageDtoBuilder builder) {
    this.senderId = builder.senderId;
    this.senderName = builder.senderName;
    this.senderAvatar = builder.senderAvatar;
    this.recipientId = builder.recipientId;
    this.content = builder.content;
    this.type = builder.type;
    this.createdAt = builder.createdAt;
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

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

}
