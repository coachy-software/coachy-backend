package life.coachy.backend.schedule

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ScheduleCrudEndpointsIntegrationSpec extends IntegrationSpec implements SampleSchedules {

  @Autowired ObjectToJsonConverter objectToJsonConverter

  def "details endpoint should return 403 when required permission missing"() {
    given: "we have one user and one schedule in system"
      setUpSchedule(id, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I go to /api/schedules/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/schedules/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isForbidden())
  }

  def "update endpoint should return 403 when required permission missing"() {
    given: "we have one user and one schedule in system"
      setUpSchedule(id, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I put to /api/schedules/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/schedules/{id}", id)
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isForbidden())
  }

  def "update endpoint should return 400 when validation not satisfied"() {
    given: "we have one user and one schedule in system"
      setUpSchedule(id, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I put to /api/schedules/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/schedules/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isBadRequest())
  }

  def "delete endpoint should return 403 when required permission missing"() {
    given: "we have one user and one schedule in system"
      setUpSchedule(id, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I delete to /api/schedules/{id}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/schedules/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      deleteEndpoint.andExpect(status().isForbidden())
  }

  def "create endpoint should return 400 when validation not satisfied"() {
    given: "we have one user and one schedule in system"
      setUpSchedule(id, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I post to /api/schedules/{id}"
      ResultActions updateEndpoint = mockMvc.perform(post("/api/schedules")
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isBadRequest())
  }


}
