package life.coachy.backend.notification.domain

import life.coachy.backend.notification.domain.dto.NotificationMessageDto
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder
import org.bson.types.ObjectId
import spock.lang.Specification

class NotificationCreatorSpec extends Specification {

  def "'NotificationMessageDto' to 'Notification'"() {
    given:
      NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
          .withSenderId(ObjectId.get())
          .withSenderName("yang160")
          .withSenderAvatar("#")
          .withRecipientId(ObjectId.get())
          .withContent("test content")
          .withType("ALERT")
          .build()
    expect:
      new NotificationCreator().from(dto) != null
  }
}
