package life.coachy.backend.conversation

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ConversationEndpointsAcceptanceSpec extends IntegrationSpec implements SampleConversations {

  def "fetchAll positive scenario"() {
    given: "we have three conversations and one user in system in system"
      ObjectId userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Sets.newHashSet("user.${userId}.read"))
      BasicDBObject firstConversation = setUpConversation(ObjectId.get(), "yang160", "yang160")
      BasicDBObject secondConversation = setUpConversation(ObjectId.get(), "yang160", "yang161")
      setUpConversation(ObjectId.get(), "unknown", "unknown")
    when: "I go to /api/conversations/{id}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/conversations/{id}", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstConversation.get("_id")}", "conversers": ["yang160", "yang160"]},
              {"identifier": "${secondConversation.get("_id")}", "conversers": ["yang160", "yang161"]}
            ]
          """))
    when: "I go to /api/conversations/{id}?size=1"
      ResultActions paginationEndpoint = mockMvc.perform(get("/api/conversations/{id}?size=1", userId)
          .with(httpBasic("yang160", "password123")))
    then: "I see only one result"
      paginationEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstConversation.get("_id")}", "conversers": ["yang160", "yang160"]}
            ]
          """))
  }

  def "fetchAll negative scenario"() {
    given: "we have three conversations and one user in system in system"
      ObjectId userId = ObjectId.get();
      setUpUser(userId, "yang160", "password123", Collections.emptySet())
      setUpConversation(ObjectId.get(), "yang160", "yang160")
      setUpConversation(ObjectId.get(), "yang160", "yang161")
      setUpConversation(ObjectId.get(), "unknown", "unknown")
    when: "I go to /api/conversations/{id}"
      ResultActions fetchAllEndpoint = mockMvc.perform(get("/api/conversations/{id}", userId)
          .with(httpBasic("yang160", "password123")))
    then:
      fetchAllEndpoint.andExpect(status().isForbidden())
  }

}
