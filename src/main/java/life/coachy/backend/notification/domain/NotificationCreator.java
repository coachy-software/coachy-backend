package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.query.NotificationQueryDto;

class NotificationCreator {

  Notification from(NotificationMessageDto dto) {
    return NotificationBuilder.create()
        .withSenderId(dto.getSenderId())
        .withSenderName(dto.getSenderName())
        .withSenderAvatar(dto.getSenderAvatar())
        .withRecipientId(dto.getRecipientId())
        .withContent(dto.getContent())
        .withType(NotificationType.valueOf(dto.getType()))
        .build();
  }

  Notification from(NotificationQueryDto dto) {
    return NotificationBuilder.create()
        .withSenderId(dto.getSenderId())
        .withSenderName(dto.getSenderName())
        .withSenderAvatar(dto.getSenderAvatar())
        .withRecipientId(dto.getRecipientId())
        .withContent(dto.getContent())
        .withType(NotificationType.valueOf(dto.getType()))
        .build();
  }

}
