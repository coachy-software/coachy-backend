package life.coachy.backend.password.domain

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.email.SmtpServerRule
import life.coachy.backend.password.domain.dto.PasswordResetCommandDto
import life.coachy.backend.password.domain.exception.EmailAlreadyExistsException
import life.coachy.backend.password.domain.exception.PasswordResetTokenNotFoundException
import life.coachy.backend.user.domain.exception.UserNotFoundException
import net.bytebuddy.utility.RandomString
import org.bson.types.ObjectId
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PasswordResetFacadeIntegrationSpec extends IntegrationSpec {

  @Rule SmtpServerRule smtpServerRule = new SmtpServerRule(2525);
  @Autowired PasswordResetFacade facade;

  def "'generateToken' method should throw `UserNotFoundException` when email doesnt match any user"() {
    when: "user tries create token"
      facade.generateToken("somewrong@email.com")
    then:
      thrown(UserNotFoundException)
  }

  def "'generateToken' method should throw 'EmailAlreadyExistsException' when token for specified email already exists"() {
    given: "we have one user and one token in system"
      setUpUser(ObjectId.get(), "yang160", "password123", "yang160@gmail.com", Collections.emptySet())
      facade.generateToken("yang160@gmail.com")
    when: "user tries create token"
      facade.generateToken("yang160@gmail.com")
    then:
      thrown(EmailAlreadyExistsException)
  }

  def "'generateToken' method should send email"() {
    given: "we have one user and one token in system"
      setUpUser(ObjectId.get(), "yang160", "password123", "yang160@gmail.com", Collections.emptySet())
    when: "user tries create token"
      facade.generateToken("yang160@gmail.com")
    then:
      smtpServerRule.getMessages().length == 1
      "yang160@gmail.com" == smtpServerRule.getMessages()[0].getAllRecipients()[0].toString()
    and:
      mongoTemplate.exists(Query.query(Criteria.where("email").is("yang160@gmail.com")), PasswordReset)
  }

  def "'resetPassword' method should throw 'PasswordResetTokenNotFoundException' when token does not exist"() {
    when: "user tries to reset password"
      facade.resetPassword(RandomString.make(32), new PasswordResetCommandDto("password123", "password123"))
    then:
      thrown(PasswordResetTokenNotFoundException)
  }

}
