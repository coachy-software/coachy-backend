package life.coachy.backend.profile

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileCrudEndpointIntegrationSpec extends IntegrationSpec implements SampleProfiles {

  @Autowired ObjectToJsonConverter toJsonConverter;

  def "fetch all endpoint should return 401 when unauthorized"() {
    when: "I go to /api/profiles"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/profiles"))
    then:
      fetchAllEndpoint.andExpect(status().isUnauthorized())
  }

  def "fetch one endpoint should return 401 when unauthorized"() {
    when: "I go to /api/profiles/{id}"
      ResultActions fetchOneEndpoint = mockMvc.perform(get("/api/profiles/{id}", ObjectId.get()))
    then:
      fetchOneEndpoint.andExpect(status().isUnauthorized())
  }

  def "update endpoint should return 403 if user does not have required permission"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do put to /api/profiles/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/profiles/{id}", ObjectId.get())
          .with(httpBasic("yang160", "password123"))
          .content(this.toJsonConverter.convert(profileUpdateDtoSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isForbidden())
  }

  def "update endpoint should return 400 if payload is not attached"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do put to /api/profiles/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/profiles/{id}", ObjectId.get())
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isBadRequest())
  }

  def "update endpoint should update the profile"() {
    given: "we have one profile and one user in system"
      setUpUser(profileCreateDtoSample.getUserId(), "yang160", "password123", Sets.newHashSet("user.${profileCreateDtoSample.getUserId()}.update"))
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I do put to /api/profiles{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/profiles/{id}", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123"))
          .content(this.toJsonConverter.convert(profileUpdateDtoSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isNoContent())
  }

  def "fetch followers endpoint should return 401 when unauthorized"() {
    when: "I go to /api/profiles/{id}/followers"
      ResultActions fetchFollowersEndpoint = mockMvc.perform(get("/api/profiles/{id}/followers", ObjectId.get()))
    then:
      fetchFollowersEndpoint.andExpect(status().isUnauthorized())
  }

  def "fetch following endpoint should return 401 when unauthorized"() {
    when: "I go to /api/profiles/{id}/following"
      ResultActions fetchFollowingEndpoint = mockMvc.perform(get("/api/profiles/{id}/following", ObjectId.get()))
    then:
      fetchFollowingEndpoint.andExpect(status().isUnauthorized())
  }

}
