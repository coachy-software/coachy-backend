package life.coachy.backend.user

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.constants.MongoCollections
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserOperationsEndpointsAcceptanceSpec extends IntegrationSpec implements SampleUsers {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "positive register scenario"() {
    given: "we have no users"
    when: "I go to /api/users/register"
      ResultActions registerEndpoint = mockMvc.perform(post("/api/users/register")
          .content(objectToJsonConverter.convert(sampleRegistrationUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then: "I have been registered"
      registerEndpoint.andExpect(status().isCreated())
    when: "I go to /api/users/me"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/users/me").with(httpBasic("yang160", "yang160")))
    then: "I see details about my account"
      detailsEndpoint.andExpect(status().isOk()).andExpect(jsonPath('$.username', is("yang160")))
  }

  def "negative register scenario"() {
    given: "we have one user named 'yang160' in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I go to /api/users/register and try register using 'yang160' username"
      ResultActions registerEndpoint = mockMvc.perform(post("/api/users/register")
          .content(objectToJsonConverter.convert(sampleRegistrationUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then:
      registerEndpoint.andExpect(status().isConflict())
  }

  def "change password positive scenario"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I post to /api/users/change-password"
      ResultActions changePasswordEndpoint = mockMvc.perform(post("/api/users/change-password")
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleChangePasswordDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "My password has changed"
      changePasswordEndpoint.andExpect(status().isNoContent())
      Map<String, Object> userProperties = mongoTemplate.findOne(Query.query(Criteria.where("username").is("yang160")), Map.class, MongoCollections.USERS)
      passwordEncoder.matches("newPassword123", userProperties.get("password"))
  }

  def "change password negative scenario"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I post to /api/users/change-password"
      ResultActions changePasswordEndpoint = mockMvc.perform(post("/api/users/change-password")
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(incorrectChangePasswordDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      changePasswordEndpoint.andExpect(status().isBadRequest())
  }

}
