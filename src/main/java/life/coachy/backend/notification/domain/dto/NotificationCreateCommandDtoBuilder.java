package life.coachy.backend.notification.domain.dto;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class NotificationCreateCommandDtoBuilder implements Buildable<NotificationCreateCommandDto> {

  ObjectId senderId;
  String senderName;
  String senderAvatar;
  ObjectId recipientId;
  String content;
  String type;

  private NotificationCreateCommandDtoBuilder() {}

  public static NotificationCreateCommandDtoBuilder create() {
    return new NotificationCreateCommandDtoBuilder();
  }

  public NotificationCreateCommandDtoBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  public NotificationCreateCommandDtoBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  public NotificationCreateCommandDtoBuilder withSenderAvatar(String senderAvatar) {
    this.senderAvatar = senderAvatar;
    return this;
  }

  public NotificationCreateCommandDtoBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
    return this;
  }

  public NotificationCreateCommandDtoBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public NotificationCreateCommandDtoBuilder withType(String type) {
    this.type = type;
    return this;
  }

  @Override
  public NotificationCreateCommandDto build() {
    return new NotificationCreateCommandDto(this);
  }

}
