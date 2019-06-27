package life.coachy.backend.profile.recommendation


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RecommendationCrudEndpointIntegrationSpec extends IntegrationSpec implements SampleRecommendations {

  @Autowired ObjectToJsonConverter toJsonConverter;

  @UncompilableByCI
  def "create endpoint should create the recommendation"() {
    given: "we have one two user and one profile in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(recommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then: "Recommendation has been created"
      createEndpoint.andExpect(status().isCreated())
  }

  def "create endpoint should return 401 when user is unauthorized"() {
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(recommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isUnauthorized())
  }

  def "create endpoint should return 400 if payload is not valid"() {
    setup: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(invalidRecommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isBadRequest())
  }

  def "create endpoint should return 404 if user's profile is null"() {
    given: "we have one two user in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(recommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isNotFound())
  }

  def "create endpoint should return 404 if recommendation's author is null"() {
    given: "we have one user and one profile in system"
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang160", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(recommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isNotFound())
  }

  def "fetch all endpoint should return all recommendations"() {
    given: "we have one two user and one profile in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
      def profile = setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "I go to /api/profiles/{id}/recommendations"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/profiles/{id}/recommendations", profile.get("userId"))
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"id": "${recommendation.get("_id")}", "profileUserId": "${recommendation.get("profileUserId")}", "from": "${recommendation.get("from")}"}
            ]
          """))
  }

  def "fetch all endpoint should return 401 when user is unauthorized"() {
    when: "I go to /api/profiles/{id}/recommendations"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/profiles/{id}/recommendations", ObjectId.get()))
    then:
      fetchAllEndpoint.andExpect(status().isUnauthorized())
  }

}
