package life.coachy.backend.notification.domain;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class NotificationBuilder implements Buildable<Notification> {

  ObjectId identifier;
  ObjectId senderId;
  String senderName;
  String senderAvatar;
  ObjectId recipientId;
  String content;
  NotificationType type;

  private NotificationBuilder() {}

  public static NotificationBuilder create() {
    return new NotificationBuilder();
  }

  public NotificationBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public NotificationBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  public NotificationBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  public NotificationBuilder withSenderAvatar(String senderAvatar) {
    this.senderAvatar = senderAvatar;
    return this;
  }

  public NotificationBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
    return this;
  }

  public NotificationBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public NotificationBuilder withType(NotificationType type) {
    this.type = type;
    return this;
  }

  @Override
  public Notification build() {
    return new Notification(this);
  }

}
