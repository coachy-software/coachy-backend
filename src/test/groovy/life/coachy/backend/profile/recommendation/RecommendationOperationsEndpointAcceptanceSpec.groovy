package life.coachy.backend.profile.recommendation


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RecommendationOperationsEndpointAcceptanceSpec extends IntegrationSpec implements SampleRecommendations {

  @Autowired ObjectToJsonConverter toJsonConverter;

  @UncompilableByCI
  def "revision positive scenario"() {
    given: "we have one user in system"
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getFrom(), "yang161", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "I do post to /api/recommendations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/recommendations")
          .with(httpBasic("yang160", "password123"))
          .content(toJsonConverter.convert(recommendationCreateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then: "Recommendation has been created"
      createEndpoint.andExpect(status().isCreated())
    when: "I do post to /api/profiles/{id}/{recommendationId}/request-revision"
      ResultActions requestRevisionEndpoint = mockMvc.perform(post(createEndpoint.andReturn().getResponse().getRedirectedUrl() + "/request-revision")
          .with(httpBasic("yang160", "password123")))
    then: "Request has been sent"
      requestRevisionEndpoint.andExpect(status().isNoContent())
    when: "I do post to /api/profiles/{id}/{recommendationId}/commit-revision"
      ResultActions commitRevisionEndpoint = mockMvc.perform(post(createEndpoint.andReturn().getResponse().getRedirectedUrl() + "/commit-revision")
          .with(httpBasic("yang161", "password123"))
          .content(toJsonConverter.convert(recommendationUpdateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then: "Recommendation has been revisioned"
      commitRevisionEndpoint.andExpect(status().isNoContent())
  }

}
