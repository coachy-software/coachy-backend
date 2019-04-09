package life.coachy.backend.board

import com.google.common.collect.Sets
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto
import life.coachy.backend.board.domain.dto.BoardUpdateCommandDto
import life.coachy.backend.board.label.LabelDto
import life.coachy.backend.board.task.TaskDto
import org.bson.types.ObjectId

trait SampleBoards {

  ObjectId id = ObjectId.get()

  BoardCreateCommandDto sampleCreateDto = new BoardCreateCommandDto("test name", Sets.newLinkedHashSet(Sets.newHashSet(
      new LabelDto(ObjectId.get(), "test name", Sets.newLinkedHashSet(Sets.newHashSet(
          new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
          new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
      ))))), id)

  BoardUpdateCommandDto sampleUpdateDto = new BoardUpdateCommandDto("test name updated", Sets.newLinkedHashSet(Sets.newHashSet(
      new LabelDto(ObjectId.get(), "test name", Sets.newLinkedHashSet(Sets.newHashSet(
          new TaskDto(ObjectId.get(), "test name", "#2b2b2b", "test content"),
          new TaskDto(ObjectId.get(), "test name 2", "#2b2b2b", "test content")
      ))))))

}
