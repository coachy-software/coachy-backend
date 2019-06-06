package life.coachy.backend.schedule

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.annotation.IfProfileValue
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ScheduleOperationsEndpointAcceptanceSpec extends IntegrationSpec implements SampleSchedules {

  @Autowired ObjectToJsonConverter jsonConverter;

  @IfProfileValue(name = "spring.profiles.active", value = "nonCI")
  def "'accept' endpoint positive scenario"() {
    given: "I have one user in system"
      setUpUser(sampleCreateDto.getCharge(), "yang160", "password123", Sets.newHashSet("user.${sampleCreateDto.getCharge()}.read"))
    when: "I post to /api/schedules"
      ResultActions createEndpoint = mockMvc.perform(post('/api/schedules')
          .with(httpBasic('yang160', 'password123'))
          .content(jsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "should create the schedule"
      createEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I go to /api/schedules/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get(createEndpoint.andReturn().getResponse().getRedirectedUrl())
          .with(httpBasic("yang160", "password123")))
    then: "I see schedule details"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.note', is("brief note")))
      def scheduleId = new JSONObject(detailsEndpoint.andReturn().getResponse().getContentAsString()).get("identifier");
    when: "I go to /api/notifications/{id}"
      ResultActions notificationEndpoint = mockMvc.perform(get("/api/notifications/{id}", sampleCreateDto.getCharge())
          .with(httpBasic("yang160", "password123")))
    then: "I see all notifications belonging to the user"
      notificationEndpoint.andExpect(status().isOk())
      def notifications = new JSONObject(notificationEndpoint.andReturn().getResponse().getContentAsString()).get("content") as JSONArray
      def token = ((JSONObject) (notifications.get(0))).get("content")
    when: "I post to /api/schedules/{id}/accept"
      ResultActions acceptEndpoint = mockMvc.perform(post('/api/schedules/{scheduleId}/accept', scheduleId)
          .with(httpBasic("yang160", "password123"))
          .content(jsonConverter.convert(Collections.singletonMap("token", token)))
          .contentType(MediaType.APPLICATION_JSON))
    then: "should accept the schedule"
      acceptEndpoint.andExpect(status().isOk())
  }

  @IfProfileValue(name = "spring.profiles.active", value = "nonCI")
  def "'accept' endpoint negative scenario"() {
    given: "I have one user in system"
      setUpUser(sampleCreateDto.getCharge(), "yang160", "password123", Sets.newHashSet("user.${sampleCreateDto.getCharge()}.read"))
    when: "I post to /api/schedules"
      ResultActions createEndpoint = mockMvc.perform(post('/api/schedules')
          .with(httpBasic('yang160', 'password123'))
          .content(jsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "should create the schedule"
      createEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I go to /api/schedules/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get(createEndpoint.andReturn().getResponse().getRedirectedUrl())
          .with(httpBasic("yang160", "password123")))
    then: "I see schedule details"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.note', is("brief note")))
      def scheduleId = new JSONObject(detailsEndpoint.andReturn().getResponse().getContentAsString()).get("identifier");
    when: "I post to /api/schedules/{id}/accept"
      ResultActions acceptEndpoint = mockMvc.perform(post('/api/schedules/{scheduleId}/accept', scheduleId)
          .with(httpBasic("yang160", "password123"))
          .content(jsonConverter.convert(Collections.singletonMap("token", "wrong token")))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      acceptEndpoint.andExpect(status().isNotFound())
  }

}
