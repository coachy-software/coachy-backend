package life.coachy.backend.user

import com.google.common.collect.Sets
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserCrudEndpointsIntegrationSpec extends IntegrationSpec implements SampleUsers {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "user update endpoint should return 401 when unauthorized"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.update"))
    when: "user tries to update an account"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/users/{id}", id)
          .with(httpBasic("yang160", "wrongPassword123"))
          .content(objectToJsonConverter.convert(sampleUpdateUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then: "return 401 because credentials does not match any user"
      updateEndpoint.andExpect(status().isUnauthorized())
  }

  def "user update endpoint should return 400 when validation not satisfied"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.update"))
    when: "user tries to update an account"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(incorrectSampleUpdateUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isBadRequest())
  }

  def "user delete endpoint should return 401 when unauthorized"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.delete"))
    when: "user tries to delete an account"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/users/{id}", id)
          .with(httpBasic("yang160", "wrongPassword123")))
    then: "return 401 because credentials does not match any user"
      deleteEndpoint.andExpect(status().isUnauthorized())
  }

  def "user update endpoint should return 409 when user with same username already exists"() {
    given: "we have two users in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id.toHexString()}.update"))
      setUpUser(ObjectId.get(), "yang160_UPDATED", "password123", Sets.newHashSet("user.${id.toHexString()}.update"))
    when: "user tries to change username"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/users/{id}", id)
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleUpdateUser).getBytes())
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isConflict())
  }

}
