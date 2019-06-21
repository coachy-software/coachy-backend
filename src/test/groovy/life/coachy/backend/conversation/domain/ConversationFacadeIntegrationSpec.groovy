package life.coachy.backend.conversation.domain


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.conversation.SampleConversations
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ConversationFacadeIntegrationSpec extends IntegrationSpec implements SampleConversations {

  @Autowired ConversationFacade facade;

  def "method 'createIfAbsent' should create a conversation"() {
    given: "we have one user in system named 'yang160'"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user tries to create conversation"
      def uri = this.facade.createIfAbsent(sampleConversationDto)
    then: "should store conversation in db"
      mongoTemplate.exists(Query.query(Criteria.where("lastMessageText").is(sampleConversationDto.getLastMessageText())), MongoCollections.CONVERSATIONS)
    and: "add 'conversation.x.read' permission to both conversers"
      String permissionName = "conversation.${uri.toString().substring(uri.toString().lastIndexOf("/") + 1)}.read"
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(user.get("_id")).and("permissions").in(permissionName)), MongoCollections.USERS)
  }

  def "method 'createIfAbsent' should do nothing when conversation exists"() {
    given: "we have one conversation and one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
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
      this.facade.updateLastMesasge(sampleConversationId, sampleConversationUpdateCommandDto)
    then: "should change the message content"
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(sampleConversationId).and("lastMessageText").is("edited")), MongoCollections.CONVERSATIONS)
  }

  def "method 'updateLastMessage' should throw 'ConversationNotFoundException' if doesn't exist"() {
    when: "user tries to update last message"
      this.facade.updateLastMesasge(ObjectId.get(), sampleConversationUpdateCommandDto)
    then:
      thrown(ConversationNotFoundException)
  }

  def "method 'create' should throw UserNotFoundException if one of the conversers does not exist"() {
    when: "user tries to create a conversation"
      this.facade.create(sampleConversationDto, { -> "" }, { -> "" })
    then:
      thrown(UserNotFoundException)
  }

  def "method 'create' should execute the exist handler if conversation with that conversers already exists"() {
    given: "we have one conversation and one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      setUpConversation(sampleConversationId, "yang160", "yang160")
    when: "user tries to create a conversation"
      def result = this.facade.create(sampleConversationDto, { uri -> "exists" }, { uri -> "does not exist" })
    then:
      result == "exists"
  }

  def "method 'create' should execute the create handler if conversation does not exist"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user tries to create a conversation"
      def result = this.facade.create(sampleConversationDto, { uri -> "exists" }, { uri -> "does not exist" })
    then:
      result == "does not exist"
  }


}
