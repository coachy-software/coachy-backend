package life.coachy.backend.notification.domain

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.notification.domain.dto.NotificationMessageDto
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

class NotificationFacadeIntegrationSpec extends IntegrationSpec {

  @Autowired private NotificationFacade notificationFacade;

  def "'sendNotificationToUser' method should throw UserNotFoundException if specified recipientId does not belong to any user"() {
    given: "notification exception"
      NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
          .withRecipientId(ObjectId.get())
          .build()
    when: "user tries to send notification"
      this.notificationFacade.sendNotificationToUser(dto)
    then:
      thrown(UserNotFoundException)
  }

}
