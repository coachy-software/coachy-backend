package life.coachy.backend.user

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserCrudEndpointAcceptanceSpec extends IntegrationSpec implements SampleUsers {

  @Autowired ObjectToJsonConverter objectToJsonConverter

  def "positive update user details scenario"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.update"))
    when: "I put /api/users/{id}"
      ResultActions usersEndpoint = mockMvc.perform(put("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleUpdateUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then: "user has been updated"
      usersEndpoint.andExpect(status().isNoContent())
    when: "I go to /api/users/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/users/{id}", id)
          .with(httpBasic("yang160_UPDATED", "password123")))
    then: "I see my account details"
      detailsEndpoint.andExpect(status().isOk()).andExpect(jsonPath('$.username', is("yang160_UPDATED")))
  }

  def "negative update user details scenario"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I put /api/users/{id}"
      ResultActions usersEndpoint = mockMvc.perform(put("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleUpdateUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then: "system returns 403 because I do not have required permission"
      usersEndpoint.andExpect(status().isForbidden())
  }

  def "positive delete account scenario"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.delete"))
    when: "I delete /api/users/{id}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then: "User has been deleted"
      deleteEndpoint.andExpect(status().isNoContent())
    when: "I go to /api/users/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then: "system returns 401 because user does not exist"
      detailsEndpoint.andExpect(status().isUnauthorized())
  }

  def "negative delete account scenario"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I delete /api/users/{id}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then: "system returns 403 because I do not have required permission"
      deleteEndpoint.andExpect(status().isForbidden())
  }

  def "should display users"() {
    given: "we have two users in system"
      BasicDBObject firstUser = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      BasicDBObject secondUser = setUpUser(ObjectId.get(), "yang161", "password123", Collections.emptySet())
    when: "I go to /api/users"
      ResultActions usersEndpoint = mockMvc.perform(get("/api/users"))
    then: "I see all users"
      usersEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstUser.get("_id")}", "username": "${firstUser.get("username")}"},
              {"identifier": "${secondUser.get("_id")}", "username": "${secondUser.get("username")}"}
            ]
          """))
    when: "I go to /api/users?page=0&size=1"
      ResultActions paginationEndpoint = mockMvc.perform(get("/api/users?page=0&size=1"))
    then: "I see only one user"
      paginationEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstUser.get("_id")}", "username": "${firstUser.get("username")}"}
            ]
          """))
    when: "I go to /api/users?username=yang161"
      ResultActions searchEndpoint = mockMvc.perform(get("/api/users?username=yang161"))
    then: "I see only one user named 'yang161'"
      searchEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${secondUser.get("_id")}", "username": "${secondUser.get("username")}"}
            ]
          """))

  }

}
