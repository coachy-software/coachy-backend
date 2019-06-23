package life.coachy.backend.profile

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileOperationsEndpointIntegrationSpec extends IntegrationSpec implements SampleProfiles {

  @UncompilableByCI
  def "follow endpoint should follow the profile"() {
    given: "we have one profile and one user in system"
      setUpUser(profileCreateDtoSample.getUserId(), "yang160", "password123", Sets.newHashSet("user.${profileCreateDtoSample.getUserId()}.update"))
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/follow"
      ResultActions followEndpoint = mockMvc.perform(post("/api/profiles/{id}/follow", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123")))
    then:
      followEndpoint.andExpect(status().isOk())
  }

  def "follow endpoint should return 403 if user does not have required permission"() {
    given: "we have one profile and one user in system"
      setUpUser(profileCreateDtoSample.getUserId(), "yang160", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/follow"
      ResultActions followEndpoint = mockMvc.perform(post("/api/profiles/{id}/follow", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123")))
    then:
      followEndpoint.andExpect(status().isForbidden())
  }

  def "follow endpoint should return 401 when user if unauthorized"() {
    given: "we have one profile and one user in system"
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/follow"
      ResultActions followEndpoint = mockMvc.perform(post("/api/profiles/{id}/follow", profileCreateDtoSample.getUserId()))
    then:
      followEndpoint.andExpect(status().isUnauthorized())
  }

  def "unfollow endpoint should unfollow the profile"() {
    given: "we have one profile and one user in system"
      setUpUser(profileCreateDtoSample.getUserId(), "yang160", "password123", Sets.newHashSet("user.${profileCreateDtoSample.getUserId()}.update"))
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/follow"
      ResultActions followEndpoint = mockMvc.perform(post("/api/profiles/{id}/follow", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123")))
    then:
      followEndpoint.andExpect(status().isOk())
    when: "I post to /api/profiles/{id}/unfollow"
      ResultActions unfollowEndpoint = mockMvc.perform(post("/api/profiles/{id}/unfollow", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123")))
    then:
      followEndpoint.andExpect(status().isOk())
  }

  def "unfollow endpoint should return 403 if user does not have required permission"() {
    given: "we have one profile and one user in system"
      setUpUser(profileCreateDtoSample.getUserId(), "yang160", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/unfollow"
      ResultActions unfollowEndpoint = mockMvc.perform(post("/api/profiles/{id}/unfollow", profileCreateDtoSample.getUserId())
          .with(httpBasic("yang160", "password123")))
    then:
      unfollowEndpoint.andExpect(status().isForbidden())
  }

  def "unfollow endpoint should return 401 when user if unauthorized"() {
    given: "we have one profile and one user in system"
      setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
    when: "I post to /api/profiles/{id}/unfollow"
      ResultActions unfollowEndpoint = mockMvc.perform(post("/api/profiles/{id}/unfollow", profileCreateDtoSample.getUserId()))
    then:
      unfollowEndpoint.andExpect(status().isUnauthorized())
  }

}
