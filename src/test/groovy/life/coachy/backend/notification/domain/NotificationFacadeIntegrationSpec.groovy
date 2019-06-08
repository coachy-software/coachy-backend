package life.coachy.backend.notification.domain

import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.notification.domain.dto.NotificationMessageDto
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class NotificationFacadeIntegrationSpec extends IntegrationSpec {

  @Autowired private NotificationFacade notificationFacade;

  def "'sendNotificationToUser' method should throw UserNotFoundException if specified recipientId does not belong to any user"() {
    given: "notification dto"
      NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
          .withRecipientId(ObjectId.get())
          .build()
    when: "user tries to send notification"
      this.notificationFacade.sendNotificationToUser(dto)
    then:
      thrown(UserNotFoundException)
  }

  def "'markAllAsRead' method should throw UserNotFoundException if specified recipientId does not belong to any user"() {
    when: "user tries to send notification"
      this.notificationFacade.markAllAsRead(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "'hasAnyUnread' method should throw UserNotFoundException if specified recipientId does not belong to any user"() {
    when: "user tries to send notification"
      this.notificationFacade.hasAnyUnread(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "'markAllAsRead' should change `read` property to true in all elements"() {
    given: "we have two notifications and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      def notification = setUpNotification(user.get("_id"))
      def notification2 = setUpNotification(user.get("_id"))
    when: "user tries to mark all notifications as read"
      this.notificationFacade.markAllAsRead(user.get("_id"))
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(notification.get("_id")).and("read").is(true)), MongoCollections.NOTIFICATIONS)
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(notification2.get("_id")).and("read").is(true)), MongoCollections.NOTIFICATIONS)
  }

  def "'hasAnyUnread' should return true if there is any notifications unread"() {
    given: "we have two notifications and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      def notification = setUpNotification(user.get("_id"))
      def notification2 = setUpNotification(user.get("_id"))
    when: "user tries to check if there is any notification unread"
      def unread = this.notificationFacade.hasAnyUnread(user.get("_id"))
    then:
      unread;
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(notification.get("_id")).and("read").is(false)), MongoCollections.NOTIFICATIONS)
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(notification2.get("_id")).and("read").is(false)), MongoCollections.NOTIFICATIONS)
  }
}
