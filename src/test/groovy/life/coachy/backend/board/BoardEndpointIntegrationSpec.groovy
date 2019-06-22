package life.coachy.backend.board

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

class BoardEndpointIntegrationSpec extends IntegrationSpec implements SampleBoards {

  @Autowired ObjectToJsonConverter objectToJsonConverter;

  def "details endpoint should return 404 when board not found"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.read"))
    when: "I go to /api/boards/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/boards/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isNotFound())
  }

  def "details endpoint should return 403 when user does not have required permission"() {
    given: "we have one user and one board in system"
      setUpUser(id, "yang160", "password123", Sets.newHashSet("board.${id}.update"))
      setUpBoard(id, "test name", id)
    when: "I go to /api/boards/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/boards/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      detailsEndpoint.andExpect(status().isForbidden())
  }

  def "update endpoint should return 404 when board not found"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.update"))
    when: "I put to /api/boards/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/boards/{id}", id)
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isNotFound())
  }

  def "update endpoint should return 403 when user does not have required permission"() {
    given: "we have one user and one board in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.read"))
      setUpBoard(id, "test name", id)
    when: "I put to /api/boards/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/boards/{id}", id)
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isForbidden())
  }

  def "update endpoint should return 400 when validation not satisfied"() {
    given: "we have one user and one board in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.update"))
      setUpBoard(id, "test name", id)
    when: "I put to /api/boards/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/boards/{id}", id)
          .with(httpBasic("yang160", "password123")))
    then:
      updateEndpoint.andExpect(status().isBadRequest())
  }

  def "create endpoint should return 400 when validation not satisfied"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I post to /api/boards"
      ResultActions createEndpoint = mockMvc.perform(post("/api/boards")
          .with(httpBasic("yang160", "password123")))
    then:
      createEndpoint.andExpect(status().isBadRequest())
  }

  def "delete label endpoint should delete the label"() {
    given: "we have one board and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.update"))
      setUpBoard(id, "test", user.get("_id"))
    when: "I do delete to /api/boards/{boardId}/labels/{labelId}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/boards/{boardId}/labels/{labelId}", id, id)
          .with(httpBasic("yang160", "password123")))
    then:
      deleteEndpoint.andExpect(status().isOk())
  }

  def "delete label endpoint should return 403 if user does not have required permission"() {
    given: "we have one board and one user in system"
      def user = setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      setUpBoard(id, "test", user.get("_id"))
    when: "I do delete to /api/boards/{boardId}/labels/{labelId}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/boards/{boardId}/labels/{labelId}", id, id)
          .with(httpBasic("yang160", "password123")))
    then:
      deleteEndpoint.andExpect(status().isForbidden())
  }

  def "delete label endpoint should return 401 if user is unauthorized"() {
    given: "we have one board in system"
      setUpBoard(id, "test", ObjectId.get())
    when: "I do delete to /api/boards/{boardId}/labels/{labelId}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/boards/{boardId}/labels/{labelId}", id, id))
    then:
      deleteEndpoint.andExpect(status().isUnauthorized())
  }

  def "delete label endpoint should return 404 if specified boardId does not belong to any existing board"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Sets.newHashSet("board.${id}.update"))
    when: "I do delete to /api/boards/{boardId}/labels/{labelId}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/boards/{boardId}/labels/{labelId}", id, id)
          .with(httpBasic("yang160", "password123")))
    then:
      deleteEndpoint.andExpect(status().isNotFound())
  }

}
