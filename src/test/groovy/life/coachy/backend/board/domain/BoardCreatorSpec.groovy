package life.coachy.backend.board.domain

import com.google.common.collect.Lists
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto
import life.coachy.backend.board.label.LabelDto
import life.coachy.backend.board.task.TaskDto
import org.bson.types.ObjectId
import spock.lang.Specification

class BoardCreatorSpec extends Specification {

  def "'BoardCreateCommandDto' to 'Board' test"() {
    given: "board data transfer object"
      BoardCreateCommandDto dto = new BoardCreateCommandDto("test name", Lists.newArrayList(
          new LabelDto(ObjectId.get(), "test name", Lists.newArrayList(
              new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
              new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
          ))), ObjectId.get())
    when: "map schedule dto to schedule entity"
      Board board = new BoardCreator().from(dto);
    then:
      board != null
  }

}

