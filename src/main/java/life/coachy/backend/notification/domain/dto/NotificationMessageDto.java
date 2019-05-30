package life.coachy.backend.notification.domain.dto;

import org.bson.types.ObjectId;

public class NotificationMessageDto {

  private ObjectId senderId;
  private String senderName;
  private String senderAvatar;
  private ObjectId recipientId;
  private String content;
  private String type;

  NotificationMessageDto() {}

  NotificationMessageDto(NotificationMessageDtoBuilder builder) {
    this.senderId = builder.senderId;
    this.senderName = builder.senderName;
    this.senderAvatar = builder.senderAvatar;
    this.recipientId = builder.recipientId;
    this.content = builder.content;
    this.type = builder.type;
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
