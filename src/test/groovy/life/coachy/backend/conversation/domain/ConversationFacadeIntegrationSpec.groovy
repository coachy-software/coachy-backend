package life.coachy.backend.conversation.domain

import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.conversation.SampleConversations
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException
import life.coachy.backend.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ConversationFacadeIntegrationSpec extends IntegrationSpec implements SampleConversations {

  @Autowired ConversationFacade facade;

  def "method 'createIfAbsent' should create a conversation"() {
    given: "we have one user in system named 'yang160'"
      BasicDBObject user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user tries to create conversation"
      this.facade.createIfAbsent(sampleConversationDto)
    then: "should store conversation in db"
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(sampleConversationId)), MongoCollections.CONVERSATIONS)
    and: "add 'conversation.x.read' permission to both conversers"
      String permissionName = "conversation.${sampleConversationId}.read"
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(user.get("_id")).and("permissions").regex(permissionName)), MongoCollections.USERS)
  }

  def "method 'createIfAbsent' should do nothing when conversation exists"() {
    given: "we have one conversation in system"
      setUpConversation(sampleConversationId, "yang160", "yang160")
    when: "user tries to create conversation"
      this.facade.createIfAbsent(sampleConversationDto)
    then: "conversation has not been created"
      mongoTemplate.exists(Query.query(Criteria.where("conversers").regex("yang160")), MongoCollections.CONVERSATIONS)
      !mongoTemplate.exists(Query.query(Criteria.where("conversers").regex("yang161")), MongoCollections.CONVERSATIONS)
  }

  def "method 'updateLastMesasge' should update conversation's last message details"() {
    given: "we have one conversation in system"
      setUpConversation(sampleConversationId, "yang160", "yang160")
    when: "user tries to update last message"
      this.facade.updateLastMesasge(sampleConversationDto, sampleConversationUpdateCommandDto)
    then: "should change the message content"
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(sampleConversationId).and("lastMessageText").is("edited")), MongoCollections.CONVERSATIONS)
  }

  def "method 'updateLastMessage' should throw 'ConversationNotFoundException' if doesn't exist"() {
    when: "user tries to update last message"
      this.facade.updateLastMesasge(sampleConversationDto, sampleConversationUpdateCommandDto)
    then:
      thrown(ConversationNotFoundException)
  }

}
