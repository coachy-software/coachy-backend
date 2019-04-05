package life.coachy.backend.password

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.email.SmtpServerRule
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import life.coachy.backend.password.domain.dto.PasswordResetCommandDto
import org.bson.types.ObjectId
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PasswordResetEndpointsAcceptanceSpecs extends IntegrationSpec {

  @Autowired ObjectToJsonConverter objectToJsonConverter;
  @Rule SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

  def "reset password positive scenario"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", "yang160@gmail.com", Collections.emptySet())
    when: "I post to /api/create-token/yang160@gmail.com"
      ResultActions createTokenEndpoint = mockMvc.perform(post("/api/create-token/yang160@gmail.com"))
    then:
      createTokenEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I post to /api/reset-password/{token}"
      ResultActions resetPasswordEndpoint = mockMvc.perform(post(createTokenEndpoint.andReturn().getResponse().getRedirectedUrl())
          .content(objectToJsonConverter.convert(new PasswordResetCommandDto("password123", "password123")))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      resetPasswordEndpoint.andExpect(status().isNoContent())

  }

  def "reset password negative scenario"() {
    when: "I post to /api/create-token/yang160@gmail.com"
      ResultActions createTokenEndpoint = mockMvc.perform(post("/api/create-token/yang160@gmail.com"))
    then:
      createTokenEndpoint.andExpect(status().isNotFound())
  }

}
