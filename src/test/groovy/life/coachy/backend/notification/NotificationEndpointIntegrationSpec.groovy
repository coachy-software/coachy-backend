package life.coachy.backend.notification

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class NotificationEndpointIntegrationSpec extends IntegrationSpec {

  def "'fetchAll' endpoint should return notifications belonging to specified id"() {
    given: "we have one user and one notification in system "
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Sets.newHashSet("user.${userId}.read"))
      def notification = setUpNotification(userId)
    when: "I go to /api/notifications/{recipiedId}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            {
              "content": [
                {"identifier": "${notification.get("_id")}", "recipientId": "${userId}", "content": "test content"}
              ]
            }
    """))
  }

  def "'fetchAll' endpoint should return 401 if user is unauthorized"() {
    when: "I go to /api/notifications/{recipiedId}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}", ObjectId.get()))
    then:
      fetchAllEndpoint.andExpect(status().isUnauthorized())
  }

  def "'fetchAll' endpoint should return 403 if user does not have required permission"() {
    given: "we have one user and one notification in system "
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Collections.emptySet())
      setUpNotification(userId)
    when: "I go to /api/notifications/{recipiedId}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isForbidden())
  }

  def "'markAllAsRead' endpoint should change 'read' property to true in all notifications"() {
    given: "we have one notification and one user in system"
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Sets.newHashSet("user.${userId}.read"))
      setUpNotification(userId)
    when: "I post to /api/notifications/{recipientId}/mark-as-read"
      ResultActions markEndpoint = mockMvc.perform(post("/api/notifications/{recipientId}/mark-as-read", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      markEndpoint.andExpect(status().isOk())
  }

  def "'markAllAsRead' endpoint should return 401 if user is unauthorized"() {
    when: "I post to /api/notifications/{recipientId}/mark-as-read"
      ResultActions markEndpoint = mockMvc.perform(post("/api/notifications/{recipientId}/mark-as-read", ObjectId.get()))
    then:
      markEndpoint.andExpect(status().isUnauthorized())
  }

  def "'markAllAsRead' endpoint should return 403 if user does not have required permission"() {
    given: "we have one notification and one user in system"
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Collections.emptySet())
      setUpNotification(userId)
    when: "I post to /api/notifications/{recipientId}/mark-as-read"
      ResultActions markEndpoint = mockMvc.perform(post("/api/notifications/{recipientId}/mark-as-read", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      markEndpoint.andExpect(status().isForbidden())
  }

  def "'hasAnyUnread' endpoint should return true/false depending on whether user does have or does not have unread notifications"() {
    given: "we have one notification and one user in system"
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Sets.newHashSet("user.${userId}.read"))
      setUpNotification(userId)
    when: "I go to /api/notifications/{recipientId}/has-unread"
      ResultActions unreadEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}/has-unread", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      unreadEndpoint.andExpect(status().isOk())
  }

  def "'hasAnyUnread' endpoint should return 401 if user is unauthorized"() {
    when: "I go to /api/notifications/{recipientId}/has-unread"
      ResultActions unreadEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}/has-unread", ObjectId.get()))
    then:
      unreadEndpoint.andExpect(status().isUnauthorized())
  }

  def "'hasAnyUnread' endpoint should return 403 if user does not have required permission"() {
    given: "we have one notification and one user in system"
      def userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Collections.emptySet())
      setUpNotification(userId)
    when: "I go to /api/notifications/{recipientId}/has-unread"
      ResultActions unreadEndpoint = mockMvc.perform(get("/api/notifications/{recipientId}/has-unread", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      unreadEndpoint.andExpect(status().isForbidden())
  }

}
