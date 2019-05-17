package life.coachy.backend.exercise.template

import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ExerciseTemplateEndpointIntegrationSpec extends IntegrationSpec {

  def "details endpoint should return 404 when template not found"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I go to /api/exercises/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/exercises/{id}", ObjectId.get())
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isNotFound())
  }

  def "details endpoint should return 401 when unauthorized"() {
    when: "I go to /api/exercises/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/exercises/{id}", ObjectId.get()))
    then:
      detailsEndpoint.andExpect(status().isUnauthorized())
  }

  def "fetch all endpoint should return 401 when unauthorized"() {
    when: "I go to /api/exercises"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/exercises"))
    then:
      fetchAllEndpoint.andExpect(status().isUnauthorized())
  }

}
