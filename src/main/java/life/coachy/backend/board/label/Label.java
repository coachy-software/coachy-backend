package life.coachy.backend.board.label;

import java.util.List;
import life.coachy.backend.board.task.TaskDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Label {

  @Id private ObjectId identifier;
  private String name;
  private List<TaskDto> tasks;

  Label() {}

  Label(ObjectId identifier, String name, List<TaskDto> tasks) {
    this.identifier = identifier;
    this.name = name;
    this.tasks = tasks;
  }

}
