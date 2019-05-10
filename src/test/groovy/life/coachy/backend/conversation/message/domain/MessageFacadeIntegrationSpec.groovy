package life.coachy.backend.conversation.message.domain

import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.conversation.message.SampleMessages
import life.coachy.backend.conversation.message.domain.exception.MessageNotFoundException
import life.coachy.backend.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class MessageFacadeIntegrationSpec extends IntegrationSpec implements SampleMessages {

  @Autowired MessageFacade messageFacade;

  def "method 'findAllByConversationId' should throw 'MessageNotFoundException' if there are no messages belong to that id"() {
    when: "user tries to fetch all messages"
      this.messageFacade.findAllByConversationId(ObjectId.get())
    then:
      thrown(MessageNotFoundException)
  }

  def "method 'findAllByConversationId' should do nothing if there are messages belong to that id"() {
    given: "we have one message in system"
      BasicDBObject message = setUpMessage(ObjectId.get(), ObjectId.get(), "test message")
    when: "user tries to fetch all messages"
      this.messageFacade.findAllByConversationId(message.get("conversationId"))
    then:
      notThrown(MessageNotFoundException)
  }

  def "method 'save' should store message"() {
    when: "user tries to save message"
      this.messageFacade.save(sampleOutputMessageDto)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(sampleMessageId)), MongoCollections.MESSAGES)
  }

}
