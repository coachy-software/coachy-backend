package life.coachy.backend.user

import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserCrudEndpointsIntegrationSpec extends IntegrationSpec {

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
              {"identifier": "${firstUser.get("_id")}", "username": "${firstUser.get("username")}", "password": "${firstUser.get("password")}"},
              {"identifier": "${secondUser.get("_id")}", "username": "${secondUser.get("username")}", "password": "${secondUser.get("password")}"}
            ]
          """))
  }

}
