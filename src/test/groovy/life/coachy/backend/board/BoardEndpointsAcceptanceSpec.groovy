package life.coachy.backend.board

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class BoardEndpointsAcceptanceSpec extends IntegrationSpec implements SampleBoards {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "board create positive scenario"() {
    given: "we have one user in system"
      BasicDBObject user = setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I post to /api/boards"
      ResultActions createEndpoint = mockMvc.perform(post("/api/boards")
          .content(objectToJsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then: "board has been created"
      createEndpoint.andExpect(status().isCreated()).andExpect(header().exists("Location"))
    when: "I go to /api/boards/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get(createEndpoint.andReturn().getResponse().getRedirectedUrl())
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.name', is("test name")))
    when: "I go to /api/users/me"
      ResultActions userDetailsEndpoint = mockMvc.perform(get("/api/users/me")
          .with(httpBasic("yang160", "password123")))
    then: "I see that permissions has changed"
      Object boardId = new JSONObject(detailsEndpoint.andReturn().getResponse().getContentAsString()).get("identifier");
      userDetailsEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            {"identifier": "${user.get("_id")}", "username": "${user.get("username")}", "password": "${user.get("password")}", "permissions": [
              "board.${boardId}.read", 
              "board.${boardId}.update"
            ]}
          """))
  }

  def "board create negative scenario"() {
    when: "I post to /api/boards"
      ResultActions createEndpoint = mockMvc.perform(post("/api/boards")
          .content(objectToJsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      createEndpoint.andExpect(status().isUnauthorized())
  }

  def "board update positive scenario"() {
    given: "we have one user and one board in system"
      setUpUser(id, "yang160", "password123", Sets.newHashSet("board.${id}.update", "board.${id}.read"))
      setUpBoard(id, "test name", id)
    when: "I put to /api/boards/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/boards/{id}", id)
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then: "board has been updated"
      updateEndpoint.andExpect(status().isNoContent())
    when: "I go to /api/boards/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/boards/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.name', is("test name updated")))
  }

  def "board update negative scenario"() {
    given: "we have one user and one board in system"
      setUpBoard(id, "test name", id)
    when: "I put to /api/boards/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/boards/{id}", id)
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isUnauthorized())
  }

}
