package life.coachy.backend.notification.domain;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class NotificationBuilder implements Buildable<Notification> {

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

  NotificationBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  NotificationBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  NotificationBuilder withSenderAvatar(String senderAvatar) {
    this.senderAvatar = senderAvatar;
    return this;
  }

  NotificationBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
    return this;
  }

  NotificationBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  NotificationBuilder withType(NotificationType type) {
    this.type = type;
    return this;
  }

  @Override
  public Notification build() {
    return new Notification(this);
  }

}
