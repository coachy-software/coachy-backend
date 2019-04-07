package life.coachy.backend.board

import com.google.common.collect.Lists
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto
import life.coachy.backend.board.domain.dto.BoardUpdateEntireEntityCommandDto
import life.coachy.backend.board.label.LabelDto
import life.coachy.backend.board.task.TaskDto
import org.bson.types.ObjectId

trait SampleBoards {

  ObjectId id = ObjectId.get()

  BoardCreateCommandDto sampleCreateDto = new BoardCreateCommandDto("test name", Lists.newArrayList(
      new LabelDto(ObjectId.get(), "test name", Lists.newArrayList(
          new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
          new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
      ))), id)

  BoardUpdateEntireEntityCommandDto sampleUpdateDto = new BoardUpdateEntireEntityCommandDto("test name updated", Lists.newArrayList(
      new LabelDto(ObjectId.get(), "test name", Lists.newArrayList(
          new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
          new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
      ))))

}
