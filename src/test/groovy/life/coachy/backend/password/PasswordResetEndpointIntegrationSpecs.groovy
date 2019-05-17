package life.coachy.backend.password

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.email.SmtpServerRule
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import life.coachy.backend.password.domain.dto.PasswordResetCommandDto
import net.bytebuddy.utility.RandomString
import org.bson.types.ObjectId
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PasswordResetEndpointIntegrationSpecs extends IntegrationSpec {

  @Autowired ObjectToJsonConverter objectToJsonConverter;
  @Rule SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

  def "create endpoint should return 409 when token already created"() {
    given: "we have one user and one token in system"
      setUpUser(ObjectId.get(), "yang160", "password123", "yang160@gmail.com", Collections.emptySet())
      mockMvc.perform(post("/api/create-token/yang160@gmail.com"))
    when: "user tries create reset token"
      ResultActions createTokenEndpoint = mockMvc.perform(post("/api/create-token/yang160@gmail.com"))
    then:
      createTokenEndpoint.andExpect(status().isConflict())
  }

  def "reset password endpoint should return 404 when token does not exist"() {
    when: "user tries reset password"
      ResultActions createTokenEndpoint = mockMvc.perform(post("/api/reset-password/{token}", RandomString.make(32))
          .content(objectToJsonConverter.convert(new PasswordResetCommandDto("password123", "password123")))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createTokenEndpoint.andExpect(status().isNotFound())
  }

}
