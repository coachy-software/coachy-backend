package life.coachy.backend.user

import life.coachy.backend.base.IntegrationSpec
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserOperationsEndpointsIntegrationSpec extends IntegrationSpec {

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

}
