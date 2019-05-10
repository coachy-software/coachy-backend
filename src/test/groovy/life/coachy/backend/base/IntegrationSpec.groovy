package life.coachy.backend.base

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import groovy.transform.TypeChecked
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.infrastructure.constant.Profiles
import org.bson.types.ObjectId
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.util.stream.Collectors

@TypeChecked
@ActiveProfiles([Profiles.TEST])
@SpringBootTest
class IntegrationSpec extends Specification {

  @Autowired protected WebApplicationContext webApplicationContext
  @Autowired PasswordEncoder passwordEncoder
  @Autowired MongoTemplate mongoTemplate;

  MockMvc mockMvc

  @Before
  void setupMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build()
  }

  def setUpUser(ObjectId id, String username, String password, Set<String> permissions) {
    this.setUpUser(id, username, password, "", permissions)
  }

  def setUpUser(ObjectId id, String username, String password, String email, Set<String> permissions) {
    Map<String, Object> userDetails = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("username", username)
        this.put("password", IntegrationSpec.this.passwordEncoder.encode(password))
        this.put("email", email)
        this.put("roles", Sets.newHashSet("USER"))
        this.put("permissions", permissions.stream().map({ permission -> permission.toString() }).collect(Collectors.toSet()))
        this.put("accountType", "CHARGE")
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(userDetails), MongoCollections.USERS)
  }

  def setUpSchedule(ObjectId id, String name, ObjectId creator, ObjectId charge) {
    Map<String, Object> scheduleDetails = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("name", name)
        this.put("creator", creator)
        this.put("charge", charge)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(scheduleDetails), MongoCollections.SCHEDULES)
  }

  def setUpExerciseTemplate(ObjectId id, String name) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("name", name)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.EXERCISES)
  }

  def setUpBoard(ObjectId id, String name, ObjectId creatorId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("name", name)
        this.put("creator", creatorId)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.BOARDS)
  }

  def setUpConversation(ObjectId id, String senderName, String recipientName) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("senderName", senderName)
        this.put("recipientName", recipientName)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.CONVERSATIONS)
  }

  def setUpMessage(ObjectId id, ObjectId conversationId, String body) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("conversationId", conversationId)
        this.put("body", body)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.MESSAGES)
  }

  void cleanup() {
    mongoTemplate.dropCollection(MongoCollections.SCHEDULES)
    mongoTemplate.dropCollection(MongoCollections.USERS)
    mongoTemplate.dropCollection(MongoCollections.BOARDS)
    mongoTemplate.dropCollection(MongoCollections.EXERCISES)
    mongoTemplate.dropCollection(MongoCollections.TOKENS)
    mongoTemplate.dropCollection(MongoCollections.CONVERSATIONS)
    mongoTemplate.dropCollection(MongoCollections.MESSAGES)
  }

}
