package life.coachy.backend.headway

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class HeadwayEndpointAcceptanceSpec extends IntegrationSpec implements SampleHeadways {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "life cycle positive scenario"() {
    given: "we have one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Sets.newHashSet("user.${sampleHeadwayId}.read"))
    when: "user tries to create a headway"
      ResultActions createEndpoint = mockMvc.perform(post('/api/headways')
          .with(httpBasic('yang160', 'password123'))
          .content(this.objectToJsonConverter.convert(sampleHeadwayCreateCommandDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "headway has been created"
      createEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I go to /api/headways/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get(createEndpoint.andReturn().getResponse().getRedirectedUrl())
          .with(httpBasic('yang160', 'password123')))
    then: "I see one headway"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
              {"ownerId": "${sampleHeadwayId}"}
           """))
    when: "I go to /api/headways/by-owner/{id}"
      ResultActions fetchAllDetailsEndpoint = mockMvc.perform(get('/api/headways/by-owner/{id}', sampleHeadwayId)
          .with(httpBasic('yang160', 'password123')))
    then: "I see headways, that belong to specified owner id"
      fetchAllDetailsEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"ownerId": "${sampleHeadwayId}"}
            ]    
           """))
    when: "I perform delete to /api/headways/{id}"
      ResultActions deleteEndpoint = mockMvc.perform(delete('/api/headways/{id}', sampleHeadwayId)
          .with(httpBasic("yang160", "password123")))
    then: "headway has been deleted"
      deleteEndpoint.andExpect(status().isOk())
  }

  def "life cycle negative scenario"() {
    when: "user tries to create a headway"
      ResultActions createEndpoint = mockMvc.perform(post('/api/headways')
          .content(this.objectToJsonConverter.convert(sampleHeadwayCreateCommandDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isUnauthorized())
  }

}
