package life.coachy.backend.conversation

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ConversationEndpointIntegrationSpec extends IntegrationSpec implements SampleConversations {

  @Autowired ObjectToJsonConverter toJsonConverter;

  def "'fetchOne' endpoint should display the conversation"() {
    given: "we have one conversation and one user in system"
      def conversation = setUpConversation(ObjectId.get(), "yang160", "yang160")
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("conversation.${conversation.get("_id")}.read"))
    when: "I go to /api/conversations/{id}"
      ResultActions fetchOneEndpoint = mockMvc.perform(get("/api/conversations/{id}", conversation.get("_id"))
          .with(httpBasic("yang160", "password123")))
    then:
      fetchOneEndpoint.andExpect(status().isOk())
  }

  def "'fetchOne' endpoint should return 403 if user does not have required permission"() {
    given: "we have one conversation and one user in system"
      def conversation = setUpConversation(ObjectId.get(), "yang160", "yang160")
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I go to /api/conversations/{id}"
      ResultActions fetchOneEndpoint = mockMvc.perform(get("/api/conversations/{id}", conversation.get("_id"))
          .with(httpBasic("yang160", "password123")))
    then:
      fetchOneEndpoint.andExpect(status().isForbidden())
  }

  def "'fetchOne' endpoint should return 401 if user is unauthorized"() {
    given: "we have one conversation in system"
      def conversation = setUpConversation(ObjectId.get(), "yang160", "yang160")
    when: "I go to /api/conversations/{id}"
      ResultActions fetchOneEndpoint = mockMvc.perform(get("/api/conversations/{id}", conversation.get("_id")))
    then:
      fetchOneEndpoint.andExpect(status().isUnauthorized())
  }

  def "'create' endpoint should return 303 (SEE_OTHER) if conversation already exists "() {
    given: "we have one conversation and one user in system"
      def conversation = setUpConversation(ObjectId.get(), "yang160", "yang160")
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("conversation.${conversation.get("_id")}.read"))
    when: "I post to /api/conversations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/conversations")
          .content(toJsonConverter.convert(sampleConversers))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      createEndpoint.andExpect(status().isSeeOther())
      createEndpoint.andReturn().getResponse().getRedirectedUrl().contains(conversation.get("_id").toString())
  }

  def "'create' endpoint should create a conversation and return 201"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I post to /api/conversations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/conversations")
          .content(toJsonConverter.convert(sampleConversers))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      createEndpoint.andExpect(status().isCreated())
  }

  def "'create' endpoint should return 401 if user is unauthorized"() {
    when: "I post to /api/conversations"
      ResultActions createEndpoint = mockMvc.perform(post("/api/conversations")
          .content(toJsonConverter.convert(sampleConversers))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isUnauthorized())
  }

}
