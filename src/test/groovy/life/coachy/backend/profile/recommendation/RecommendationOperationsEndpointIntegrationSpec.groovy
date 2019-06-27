package life.coachy.backend.profile.recommendation

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

class RecommendationOperationsEndpointIntegrationSpec extends IntegrationSpec implements SampleRecommendations {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "request revision endpoint should return 403 if user does not have required permission"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/request-revision"
      ResultActions requestRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/request-revision", ObjectId.get(), ObjectId.get())
          .with(httpBasic("yang160", "password123")))
    then:
      requestRevisionEndpoint.andExpect(status().isForbidden())
  }

  def "request revision endpoint should return 401 when user is unauthorized"() {
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/request-revision"
      ResultActions requestRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/request-revision", ObjectId.get(), ObjectId.get()))
    then:
      requestRevisionEndpoint.andExpect(status().isUnauthorized())
  }

  def "request revision endpoint should return 404 if recommendation's creator is not present"() {
    given: "we have two users and one profile in system"
      def profile = setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
      def recommendation = setUpRecommendation(ObjectId.get(), profile.get("userId"), ObjectId.get())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang160", "password123", Sets.newHashSet("recommendation.${recommendation.get("_id")}.owner"))
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/request-revision"
      ResultActions requestRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/request-revision", profile.get("userId"), recommendation.get("_id"))
          .with(httpBasic("yang160", "password123")))
    then:
      requestRevisionEndpoint.andExpect(status().isNotFound())
  }

  def "commit revision endpoint should return 403 is user does not have required permission"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/commit-revision"
      ResultActions commitRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/commit-revision", ObjectId.get(), ObjectId.get())
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(recommendationUpdateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      commitRevisionEndpoint.andExpect(status().isForbidden())
  }

  def "commit revision endpoint should return 401 when user is unauthorized"() {
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/commit-revision"
      ResultActions commitRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/commit-revision", ObjectId.get(), ObjectId.get())
          .content(objectToJsonConverter.convert(recommendationUpdateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      commitRevisionEndpoint.andExpect(status().isUnauthorized())
  }

  def "commit revision endpoint should return 404 if recommendation's id does not belong to any entity"() {
    given: "we have one user and one recommendation in system"
      def user = setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Sets.newHashSet("recommendation.${recommendationCreateSample.getProfileUserId()}.update"))
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/commit-revision"
      ResultActions commitRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/commit-revision", user.get("_id"), recommendationCreateSample.getProfileUserId())
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(recommendationUpdateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      commitRevisionEndpoint.andExpect(status().isNotFound())
  }

  def "commit revision endpoint should return 404 if user does not exist"() {
    given: "we have one user and one recommendation in system"
      def recommendation = setUpRecommendation(ObjectId.get(), ObjectId.get(), ObjectId.get())
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("recommendation.${recommendation.get("_id")}.update"))
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/commit-revision"
      ResultActions commitRevisionEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/commit-revision", user.get("_id"), recommendation.get("_id"))
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(recommendationUpdateSample))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      commitRevisionEndpoint.andExpect(status().isNotFound())
  }

  def "change visibility status endpoint should return 403 if user does not have required permission"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/visibility"
      ResultActions visibilityEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/visibility", ObjectId.get(), ObjectId.get())
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(Collections.singletonMap("visible", true)))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      visibilityEndpoint.andExpect(status().isForbidden())
  }

  def "change visibility status endpoint should return 401 when user is unauthorized"() {
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/visibility"
      ResultActions visibilityEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/visibility", ObjectId.get(), ObjectId.get())
          .content(objectToJsonConverter.convert(Collections.singletonMap("visible", true)))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      visibilityEndpoint.andExpect(status().isUnauthorized())
  }

  def "change visibility status should change visibility of the recommendation"() {
    given: "we have one user and one recommendation in system"
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getProfileUserId())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang160", "password123", Sets.newHashSet("recommendation.${recommendation.get("_id")}.owner"))
    when: "I do post to /api/profiles/{id}/recommendations/{recommendationId}/visibility"
      ResultActions visibilityEndpoint = mockMvc.perform(post("/api/profiles/{id}/recommendations/{recommendationId}/visibility", ObjectId.get(), recommendation.get("_id"))
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(Collections.singletonMap("visible", true)))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      visibilityEndpoint.andExpect(status().isNoContent())
  }

}
