package life.coachy.backend.headway

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HeadwayEndpointIntegrationSpec extends IntegrationSpec implements SampleHeadways {

  def "'fetchAll' endpoint should return 403 when required permission is missing"() {
    given: "we have one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Collections.emptySet())
    when: "user tries to displays all headways belonging to it's id"
      ResultActions fetchAllEndpoint = mockMvc.perform(get('/api/headways/by-owner/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then:
      fetchAllEndpoint.andExpect(status().isForbidden())
  }

  def "'delete' endpoint should return 403 when required permission is missing"() {
    given: "we have one user and one headway in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Collections.emptySet())
      setUpHeadway(sampleHeadwayId, sampleHeadwayId)
    when: "user tries to delete the headway"
      ResultActions deleteEndpoint = mockMvc.perform(delete('/api/headways/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then:
      deleteEndpoint.andExpect(status().isForbidden())
  }

  def "'delete' endpoint should return 404 if id does not match any headway"() {
    given: "we have one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${sampleHeadwayId}.delete"))
    when: "user tries to delete the headway"
      ResultActions deleteEndpoint = mockMvc.perform(delete('/api/headways/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then:
      deleteEndpoint.andExpect(status().isNotFound())
  }

  def "'fetchOne' endpoint should return 404 if id does not match any headway"() {
    given: "we have one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${sampleHeadwayId}.read"))
    when: "user tries to fetch headway"
      ResultActions fetchEndpoint = mockMvc.perform(get('/api/headways/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then:
      fetchEndpoint.andExpect(status().isNotFound())
  }

  def "'fetchOne' endpoint should return 403 when required permission is missing"() {
    given: "we have one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Collections.emptySet())
    when: "user tries to fetch headway"
      ResultActions fetchEndpoint = mockMvc.perform(get('/api/headways/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then:
      fetchEndpoint.andExpect(status().isForbidden())
  }

}
