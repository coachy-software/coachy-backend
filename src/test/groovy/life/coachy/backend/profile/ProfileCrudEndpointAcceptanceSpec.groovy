package life.coachy.backend.profile

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import org.bson.types.ObjectId
import org.hamcrest.Matchers
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ProfileCrudEndpointAcceptanceSpec extends IntegrationSpec implements SampleProfiles {

  @UncompilableByCI
  def "following positive scenario"() {
    given: "we have two profiles and two user in system"
      def followed = setUpUser(profileSampleId, "yang161", "password123", Sets.newHashSet("user.${profileSampleId}.read", "user.${profileSampleId}.update"))
      def follower = setUpUser(secondProfileSampleId, "yang160", "password123", Sets.newHashSet("user.${secondProfileSampleId}.read", "user.${secondProfileSampleId}.update"))

      def followedProfile = setUpProfile(ObjectId.get(), followed.get("_id"))
      setUpProfile(ObjectId.get(), follower.get("_id"))
    when: "I do post to /api/profiles/{id}/follow"
      ResultActions followEndpoint = mockMvc.perform(post("/api/profiles/{id}/follow", followedProfile.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then: "user has been followed"
      followEndpoint.andExpect(status().isOk())
    when: "I go to /api/profiles/{id}/followers"
      ResultActions followersEndpoint = mockMvc.perform(get("/api/profiles/{id}/followers", followedProfile.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then: "I see one result with username 'yang160'"
      followersEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath("\$.followers[0].username", is(follower.get("username"))))
    when: "I post to /api/profiles/{id}/unfollow"
      ResultActions unfollowEndpoint = mockMvc.perform(post("/api/profiles/{id}/unfollow", followedProfile.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then: "user has been unfollowed"
      unfollowEndpoint.andExpect(status().isOk())
    when: "I go to /api/profiles/{id}/followers"
      ResultActions emptyFollowersEndpoint = mockMvc.perform(get("/api/profiles/{id}/followers", followedProfile.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then: "I see one result with username 'yang160'"
      emptyFollowersEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath("\$.followers", Matchers.emptyCollectionOf(Set)))
  }

  def "should display profiles"() {
    given: "we have two profiles two one users in system"
      setUpUser(profileSampleId, "yang160", "password123", Collections.emptySet())
      setUpUser(secondProfileSampleId, "yang161", "password123", Collections.emptySet())

      def profile1 = setUpProfile(ObjectId.get(), profileSampleId)
      def profile2 = setUpProfile(ObjectId.get(), secondProfileSampleId)
    when: "I go to /api/profiles"
      ResultActions profilesEndpoint = mockMvc.perform(get("/api/profiles")
          .with(httpBasic("yang160", "password123")))
    then: "I see all profiles"
      profilesEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${profile1.get("_id")}", "userId": "${profileSampleId}", "username": "yang160"},
              {"identifier": "${profile2.get("_id")}", "userId": "${secondProfileSampleId}", "username": "yang161"}
            ]
          """))
    when: "I go to /api/profiles/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/profiles/{id}", profile2.get("userId").toString())
          .with(httpBasic("yang160", "password123")))
    then: "I see profile's details"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath("\$.identifier", Matchers.is(profile2.get("_id").toString())))
    when: "I go to /api/profiles?page=0&size=1"
      ResultActions paginationEndpoint = mockMvc.perform(get("/api/profiles?page=0&size=1")
          .with(httpBasic("yang160", "password123")))
    then: "I see only one profile"
      paginationEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${profile1.get("_id")}", "userId": "${profile1.get("userId")}"}
            ]
          """))
    when: "I go to /api/profiles?userId={id}"
      ResultActions searchEndpoint = mockMvc.perform(get("/api/profiles?userId=${profile1.get("userId")}")
          .with(httpBasic("yang160", "password123")))
    then: "I see only one profile"
      searchEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${profile1.get("_id")}", "userId": "${profile1.get("userId")}"}
            ]
          """))

  }

}
