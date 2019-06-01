package life.coachy.backend.notification


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.Shell

class NotificationCommandIntegrationSpec extends IntegrationSpec {

  @Autowired private Shell shell;

  def "'send' command should send message if user exists"() {
    given: "we have one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user types command"
      Object result = this.shell.evaluate({ -> "send ${user.get("_id")} test".toString() })
    then:
      result == /The message has been sent to: ${user.get("_id")} with content: test/
  }

  def "'send' command should throw UserNotFoundException if user does not exist"() {
    when: "user types command"
      def result = this.shell.evaluate({ -> "send ${ObjectId.get()} test".toString() })
    then:
      result instanceof UserNotFoundException
  }

  def "'sendall' command should send message to all users"() {
    when: "system sends a notification"
      def result = this.shell.evaluate({ -> "sendall test" })
    then:
    "The message: test has been sent to everyone." == result
  }

}
