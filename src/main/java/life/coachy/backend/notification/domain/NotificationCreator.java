package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.domain.dto.NotificationCreateCommandDto;

class NotificationCreator {

  Notification from(NotificationCreateCommandDto dto) {
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
