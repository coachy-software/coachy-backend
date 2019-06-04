package life.coachy.backend.request.domain

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.request.domain.exception.RequestNotFoundException
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

class RequestFacadeIntegrationSpec extends IntegrationSpec {

  @Autowired RequestFacade requestFacade;

  def "'invalidateToken' method should throw 'RequestNotFoundException' if token does not belong to any request"() {
    when: "user tries to invalidate a token"
      requestFacade.invalidateToken("1234567890", { -> println "It is not going to happen" })
    then:
      thrown(RequestNotFoundException)
  }

  def "'createToken' method should throw 'UserNotFoundException' if user with specified does not exist"() {
    when: "user tries to create a token"
      requestFacade.createToken(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "'fetchToken' method should throw 'RequestNotFoundException' if token does not belong to any request"() {
    when: "user tries to fetch a token"
      requestFacade.fetchToken("1234567890")
    then:
      thrown(RequestNotFoundException)
  }

  def "'fetchAllByRequesterId' method should throw 'UserNotFoundException' if user with specified does not exist"() {
    when: "user tries to fetch all requests by requester identifier"
      requestFacade.fetchAllByRequesterId(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

}
