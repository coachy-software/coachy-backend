package life.coachy.backend.profile

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.hamcrest.Matchers
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ProfileCrudEndpointAcceptanceSpec extends IntegrationSpec implements SampleProfiles {

  def "should display profiles"() {
    given: "we have two profiles and one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("user.${profileCreateDtoSample.getUserId()}.read"))
      def profile1 = setUpProfile(ObjectId.get(), profileCreateDtoSample.getUserId())
      def profile2 = setUpProfile(ObjectId.get(), ObjectId.get())
    when: "I go to /api/profiles"
      ResultActions profilesEndpoint = mockMvc.perform(get("/api/profiles")
          .with(httpBasic("yang160", "password123")))
    then: "I see all profiles"
      profilesEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${profile1.get("_id")}", "userId": "${profile1.get("userId")}"},
              {"identifier": "${profile2.get("_id")}", "userId": "${profile2.get("userId")}"}
            ]
          """))
    when: "I go to /api/profiles/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/profiles/{id}", profile1.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then: "I see profile's details"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath("\$.identifier", Matchers.is(profile1.get("_id").toString())))
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
      ResultActions searchEndpoint = mockMvc.perform(get("/api/profiles?userId=${profile2.get("userId")}")
          .with(httpBasic("yang160", "password123")))
    then: "I see only one profile"
      searchEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${profile2.get("_id")}", "userId": "${profile2.get("userId")}"}
            ]
          """))

  }

}
