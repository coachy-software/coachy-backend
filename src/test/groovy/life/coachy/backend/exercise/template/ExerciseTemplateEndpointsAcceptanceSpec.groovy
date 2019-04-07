package life.coachy.backend.exercise.template


import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ExerciseTemplateEndpointsAcceptanceSpec extends IntegrationSpec {

  def "should display templates"() {
    given: "we have two templates and one user in system"
      BasicDBObject firstTemplate = setUpExerciseTemplate(ObjectId.get(), "test 1")
      BasicDBObject secondTemplate = setUpExerciseTemplate(ObjectId.get(), "test two")
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I go to /api/exercises"
      ResultActions exercisesEndpoint = mockMvc.perform(get("/api/exercises")
          .with(httpBasic("yang160", "password123")))
    then:
      exercisesEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstTemplate.get("_id")}", "name": "${firstTemplate.get("name")}"},
              {"identifier": "${secondTemplate.get("_id")}", "name": "${secondTemplate.get("name")}"}
            ]
          """))
    when: "I go to /api/exercises/{id}"
      ResultActions exerciseEndpoint = mockMvc.perform(get("/api/exercises/{id}", firstTemplate.get("_id"))
          .with(httpBasic("yang160", "password123")))
    then:
      exerciseEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            {"identifier": "${firstTemplate.get("_id")}", "name": "${firstTemplate.get("name")}"}
          """))
    when: "I go to /api/exercises?page=0&size=1"
      ResultActions paginationEndpoint = mockMvc.perform(get("/api/exercises?page=0&size=1")
          .with(httpBasic("yang160", "password123")))
    then: "I see only one template"
      paginationEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${firstTemplate.get("_id")}", "name": "${firstTemplate.get("name")}"}
            ]
          """))
    when: "I go to /api/exercises?name=test%20two"
      ResultActions searchEndpoint = mockMvc.perform(get(new URI("/api/exercises?name=test%20two"))
          .with(httpBasic("yang160", "password123"))
          .contentType(MediaType.APPLICATION_JSON))
    then: "I see only one template named 'test two'"
      searchEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${secondTemplate.get("_id")}", "name": "${secondTemplate.get("name")}"}
            ]
          """))
  }

}
