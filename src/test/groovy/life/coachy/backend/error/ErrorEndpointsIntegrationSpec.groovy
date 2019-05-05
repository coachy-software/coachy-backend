package life.coachy.backend.error

import life.coachy.backend.base.IntegrationSpec
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

class ErrorEndpointsIntegrationSpec extends IntegrationSpec {

  def "error endpoint tests"() {
    when: "I go to /error"
      ResultActions errorEndpoint = mockMvc.perform(get("/error"))
    then:
      errorEndpoint.andExpect(content().json("""
        {
            "code": 200,
            "message": "None"
        }  
      """))
  }

}
