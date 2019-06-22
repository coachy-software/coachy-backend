package life.coachy.backend.board.domain

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.board.SampleBoards
import life.coachy.backend.board.domain.exception.BoardNotFoundException
import life.coachy.backend.board.query.BoardQueryDto
import life.coachy.backend.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class BoardFacadeIntegrationSpec extends IntegrationSpec implements SampleBoards {

  @Autowired BoardFacade facade;

  def "'create' method should store user"() {
    given: "we have one user in system"
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "user tries to create board"
      facade.create(sampleCreateDto)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("name").is("test name")), Board)
  }

  def "'update' should update a schedule"() {
    given: "we have one board and one user in system"
      setUpUser(id, "yang160", "password123", Sets.newHashSet("board.${id}.update"))
      setUpBoard(id, "test name", id)
    when: "user tries to update the board"
      facade.update(id, sampleUpdateDto)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(id).and("name").is("test name updated")), Board)
  }

  def "'update' method should throw 'BoardNotFoundException' if board does not exist"() {
    when: "user tries to update the board"
      facade.update(ObjectId.get(), sampleUpdateDto)
    then:
      thrown(BoardNotFoundException)
  }

  def "method 'fetchOne' should return a board"() {
    given: "we have one board in system"
      BasicDBObject board = setUpBoard(ObjectId.get(), "test name", ObjectId.get())
    when: "user tries to get board details"
      BoardQueryDto queryDto = facade.fetchOne(board.get("_id"))
    then: "I see board query exception"
      queryDto.name == "test name"
      queryDto.identifier == board.get("_id")
  }

  def "method 'fetchOne' should throw 'BoardNotFoundException' if board does not exist"() {
    when: "user tries to get board details"
      facade.fetchOne(ObjectId.get())
    then:
      thrown(BoardNotFoundException)
  }

  def "method 'deleteLabel' should delete the label"() {
    given: "we have one board in system"
      setUpBoard(id, "test", ObjectId.get())
    and:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(id).and("labels").size(1)), MongoCollections.BOARDS)
    when: "user tries to delete the label"
      facade.deleteLabel(id, id)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(id).and("labels").size(0)), MongoCollections.BOARDS)
  }

  def "method 'deleteLabel' should throw 'BoardNotFoundException' if board does not exist"() {
    when: "user tries to delete the label"
      facade.deleteLabel(id, id)
    then:
      thrown(BoardNotFoundException)
  }

}
