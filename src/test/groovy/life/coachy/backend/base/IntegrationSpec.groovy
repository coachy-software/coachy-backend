package life.coachy.backend.base

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import groovy.transform.TypeChecked
import life.coachy.backend.board.label.LabelDto
import life.coachy.backend.board.task.TaskDto
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

import java.time.LocalDateTime
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
        this.put("labels", Sets.newHashSet(new LabelDto(id, "test name", Sets.newLinkedHashSet(Sets.newHashSet(
            new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
            new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
        )))))
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.BOARDS)
  }

  def setUpConversation(ObjectId id, String senderName, String recipientName) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("conversers", Lists.newArrayList(senderName, recipientName))
        this.put("lastMessageDate", LocalDateTime.now())
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

  def setUpHeadway(ObjectId id, ObjectId ownerId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("conversationId", ownerId)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.HEADWAYS)
  }

  def setUpNotification(ObjectId recipientId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", ObjectId.get())
        this.put("content", "test content")
        this.put("recipientId", recipientId)
        this.put("type", "ALERT")
        this.put("read", false)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.NOTIFICATIONS)
  }

  def setUpRequest(ObjectId requesterId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", ObjectId.get())
        this.put("token", "1234567890")
        this.put("requesterId", requesterId)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.REQUESTS)
  }

  def setUpProfile(ObjectId profileId, ObjectId userId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", profileId)
        this.put("userId", userId)
        this.put("website", "website")
        this.put("followers", Sets.newHashSet())
        this.put("following", Sets.newHashSet())
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.PROFILES)
  }

  def setUpRecommendation(ObjectId id, ObjectId profileUserId, ObjectId fromId) {
    Map<String, Object> templateDetials = new HashMap<String, Object>() {
      {
        this.put("_id", id)
        this.put("profileUserId", profileUserId)
        this.put("from", fromId)
      }
    }

    return this.mongoTemplate.insert(new BasicDBObject(templateDetials), MongoCollections.RECOMMENDATIONS)
  }

  void cleanup() {
    mongoTemplate.dropCollection(MongoCollections.SCHEDULES)
    mongoTemplate.dropCollection(MongoCollections.USERS)
    mongoTemplate.dropCollection(MongoCollections.BOARDS)
    mongoTemplate.dropCollection(MongoCollections.EXERCISES)
    mongoTemplate.dropCollection(MongoCollections.TOKENS)
    mongoTemplate.dropCollection(MongoCollections.CONVERSATIONS)
    mongoTemplate.dropCollection(MongoCollections.MESSAGES)
    mongoTemplate.dropCollection(MongoCollections.HEADWAYS)
    mongoTemplate.dropCollection(MongoCollections.NOTIFICATIONS)
    mongoTemplate.dropCollection(MongoCollections.REQUESTS)
    mongoTemplate.dropCollection(MongoCollections.PROFILES)
    mongoTemplate.dropCollection(MongoCollections.RECOMMENDATIONS)
  }

}
