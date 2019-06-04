package life.coachy.backend.request


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.shell.Shell

class RequestCommandIntegrationCommand extends IntegrationSpec {

  @Autowired Shell shell

  def "'mktkn' command should store a reqest in system"() {
    given: "we have one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user types the command"
      def result = this.shell.evaluate({ -> "mktkn ${user.get("_id")}".toString() })
    then:
      result == "Token has been successfully created"
      mongoTemplate.exists(Query.query(Criteria.where("requesterId").is(user.get("_id"))), MongoCollections.REQUESTS)
  }

  def "'mktkn' command should return not found message if user does not exist"() {
    given: "random id"
      def id = ObjectId.get()
    when: "user types the command"
      def result = this.shell.evaluate({ -> "mktkn ${id}".toString() })
    then:
      result == "User with id: ${id} does not exist in system!"
      !mongoTemplate.exists(Query.query(Criteria.where("requesterId").is(id)), MongoCollections.REQUESTS)
  }

  def "'tkn' command should display request details"() {
    given: "we have one request and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      setUpRequest(user.get("_id"))
    when: "user types the command"
      String result = this.shell.evaluate({ -> "tkn 1234567890" })
    then:
      result.contains(/"token":"1234567890"/)
  }

  def "'tkn' command should return not found message if request does not exist"() {
    when: "user types the command"
      String result = this.shell.evaluate({ -> "tkn 1234567890" })
    then:
      result == "Request with token: 1234567890 does not exist in system!"
  }

  def "'alltkn' command should display all request belonging to user with specified id"() {
    given: "we have two requests and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      def request = setUpRequest(user.get("_id"))
      def request2 = setUpRequest(user.get("_id"))
    when: "user types command"
      String result = this.shell.evaluate({ -> "alltkn ${user.get("_id")}".toString() })
    then:
      result.contains(request.get("_id").toString())
      result.contains(request2.get("_id").toString())
  }

  def "'invalidatetkn' should delete the request from system"() {
    given: "we have one user and one request in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      def request = setUpRequest(user.get("_id"))
    when: "user types command"
      String result = this.shell.evaluate({ -> "invalidatetkn 1234567890" })
    then:
      result == "Token has been invalidated"
      !mongoTemplate.exists(Query.query(Criteria.where("_id").is(request.get("_id"))), MongoCollections.REQUESTS)
  }
}
