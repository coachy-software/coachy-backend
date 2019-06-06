package life.coachy.backend.schedule

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ScheduleOperationsEndpointIntegrationSpec extends IntegrationSpec implements SampleSchedules {

  @Autowired ObjectToJsonConverter jsonConverter;

  def "'accept' endpoint should return 403 if user does not have required permission"() {
    given: "we have one schedule and user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      def schedule = setUpSchedule(ObjectId.get(), "test", user.get("_id"), user.get("_id"))
      def request = setUpRequest(user.get("_id"))
    when: "I post to /api/schedules/{id}/accept"
      ResultActions acceptEndpoint = mockMvc.perform(post('/api/schedules/{id}/accept', schedule.get("_id"))
          .with(httpBasic('yang160', 'password123'))
          .content(jsonConverter.convert(Collections.singletonMap("token", request.get("token"))))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      acceptEndpoint.andExpect(status().isForbidden())
  }

  def "'accept' endpoint should return 400 if payload hasn't been provided"() {
    given: "we have one schedule and user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("schedule.${id}.accept"))
      def schedule = setUpSchedule(id, "test", user.get("_id"), user.get("_id"))
      def request = setUpRequest(user.get("_id"))
    when: "I post to /api/schedules/{id}/accept"
      ResultActions acceptEndpoint = mockMvc.perform(post('/api/schedules/{id}/accept', schedule.get("_id"))
          .with(httpBasic('yang160', 'password123'))
          .content(jsonConverter.convert(Collections.singletonMap("wrongToken", request.get("token"))))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      acceptEndpoint.andExpect(status().isBadRequest())
  }
}
