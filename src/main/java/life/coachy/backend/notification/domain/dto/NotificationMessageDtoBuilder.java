package life.coachy.backend.notification.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class NotificationMessageDtoBuilder implements Buildable<NotificationMessageDto> {

  ObjectId senderId;
  String senderName;
  String senderAvatar;
  ObjectId recipientId;
  String content;
  String type;
  LocalDateTime createdAt;

  private NotificationMessageDtoBuilder() {}

  public static NotificationMessageDtoBuilder create() {
    return new NotificationMessageDtoBuilder();
  }

  public NotificationMessageDtoBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  public NotificationMessageDtoBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  public NotificationMessageDtoBuilder withSenderAvatar(String senderAvatar) {
    this.senderAvatar = senderAvatar;
    return this;
  }

  public NotificationMessageDtoBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
    return this;
  }

  public NotificationMessageDtoBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public NotificationMessageDtoBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public NotificationMessageDtoBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public NotificationMessageDto build() {
    return new NotificationMessageDto(this);
  }

}
