package life.coachy.backend.conversation.message

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.conversation.SampleConversations
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessageEndpointsAcceptanceSpec extends IntegrationSpec implements SampleConversations {

  def "fetch all endpoint positive scnario"() {
    given: "we have two messages, one conversation and one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("conversation.${sampleConversationId}.read"))
      setUpMessage(ObjectId.get(), ObjectId.get(), "test message")

      setUpConversation(sampleConversationId, "yang160", "yang160")
      BasicDBObject firstMessage = setUpMessage(ObjectId.get(), sampleConversationId, "test message")
    when: "I go to /api/messages/{id}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/messages/{id}", sampleConversationId)
          .with(httpBasic("yang160", "password123")))
    then: "I see one result"
      fetchAllEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstMessage.get("_id")}", "conversationId": "${firstMessage.get("conversationId")}", "body": "${firstMessage.get("body")}"}
            ]
          """))
  }

  def "fetch all endpoint negative scnario"() {
    given: "we have two messages, one conversation and one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      setUpMessage(ObjectId.get(), ObjectId.get(), "test message")

      setUpConversation(sampleConversationId, "yang160", "yang160")
      BasicDBObject firstMessage = setUpMessage(ObjectId.get(), sampleConversationId, "test message")
    when: "I go to /api/messages/{id}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/messages/{id}", sampleConversationId)
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isForbidden())
  }

}
