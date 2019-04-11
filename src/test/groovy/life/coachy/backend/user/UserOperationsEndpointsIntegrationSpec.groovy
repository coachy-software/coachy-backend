package life.coachy.backend.user

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserOperationsEndpointsIntegrationSpec extends IntegrationSpec implements SampleUsers {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "user details endpoint should return 401 when unauthorized"() {
    when: "I go to /api/users/me"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/users/me").with(httpBasic("yang160", "yang160")))
    then:
      detailsEndpoint.andExpect(status().isUnauthorized())
  }

  def "registration endpoint should return 400 when validation not satisfied"() {
    when: "I go to /api/users/register"
      ResultActions registrationEndpoint = mockMvc.perform(post("/api/users/register"))
    then:
      registrationEndpoint.andExpect(status().isBadRequest())
  }

  def "change password endpoint should return 400 when old password is incorrect"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password1234", Collections.emptySet())
    when: "I go to /api/users/change-password"
      ResultActions changePasswordEndpoint = mockMvc.perform(post("/api/users/change-password")
          .with(httpBasic("yang160", "password1234"))
          .content(objectToJsonConverter.convert(sampleChangePasswordDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      changePasswordEndpoint.andExpect(status().isBadRequest())
  }

}
