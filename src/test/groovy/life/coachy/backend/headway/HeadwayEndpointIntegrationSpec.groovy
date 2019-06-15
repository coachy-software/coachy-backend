package life.coachy.backend.headway

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HeadwayEndpointIntegrationSpec extends IntegrationSpec implements SampleHeadways {

  @Autowired ObjectToJsonConverter toJsonConverter;

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

  def "'share' endpoint should return 403 when required permission is missing"() {
    given: "we have one user in system"
      def user = setUpUser(sampleHeadwayId, "yang160", "password123", Collections.emptySet())
      setUpHeadway(sampleHeadwayId, user.get("_id"))
    when: "user tries to share the headway to other user"
      ResultActions shareEndpoint = mockMvc.perform(post('/api/headways/{id}/share', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123'))
          .content(toJsonConverter.convert(Collections.singletonMap("shareTo", "notId")))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      shareEndpoint.andExpect(status().isForbidden())
  }

  def "'share' endpoint should return 404 if headway does not exist"() {
    given: "we have one user in system"
      def testId = ObjectId.get()
      setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${testId}.read"))
    when: "user tries to share the headway to other user"
      ResultActions shareEndpoint = mockMvc.perform(post('/api/headways/{id}/share', testId)
          .with(httpBasic('yang160', 'password123'))
          .content(toJsonConverter.convert(Collections.singletonMap("shareTo", sampleHeadwayId.toHexString())))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      shareEndpoint.andExpect(status().isNotFound())
  }

  def "'share' endpoint should return 404 if shareTo (id) does not belong to any user"() {
    given: "we have one user and one headway in system"
      def user = setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${sampleHeadwayId}.read"))
      setUpHeadway(sampleHeadwayId, user.get("_id"))
    when: "user tries to share the headway to other user"
      ResultActions shareEndpoint = mockMvc.perform(post('/api/headways/{id}/share', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123'))
          .content(toJsonConverter.convert(Collections.singletonMap("shareTo", ObjectId.get().toHexString())))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      shareEndpoint.andExpect(status().isNotFound())
  }

  def "'share' endpoint should return 400 if payload is incorrect"() {
    given: "we have one user and one headway in system"
      def user = setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${sampleHeadwayId}.read"))
      setUpHeadway(sampleHeadwayId, user.get("_id"))
    when: "user tries to share the headway to other user"
      ResultActions shareEndpoint = mockMvc.perform(post('/api/headways/{id}/share', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123'))
          .content(toJsonConverter.convert(Collections.singletonMap("test", "test")))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      shareEndpoint.andExpect(status().isBadRequest())
  }

  @UncompilableByCI
  def "'share' endpoint should share the headway to specified user"() {
    given: "we have one user and one headway in system"
      def user = setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("headway.${sampleHeadwayId}.read"))
      setUpHeadway(sampleHeadwayId, user.get("_id"))
    when: "user tries to share the headway to other user"
      ResultActions shareEndpoint = mockMvc.perform(post('/api/headways/{id}/share', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123'))
          .content(toJsonConverter.convert(Collections.singletonMap("shareTo", ((ObjectId) user.get("_id")).toHexString())))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      shareEndpoint.andExpect(status().isOk())
  }

}
