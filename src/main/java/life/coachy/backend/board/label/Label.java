package life.coachy.backend.board.label;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.task.dto.TaskDto;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Label implements IdentifiableEntity<ObjectId> {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId identifier;
  private String name;
  private List<TaskDto> tasks;

  Label() {}

  public Label(ObjectId identifier, String name, List<TaskDto> tasks) {
    this.identifier = identifier;
    this.name = name;
    this.tasks = tasks;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<TaskDto> getTasks() {
    return this.tasks;
  }

  public void setTasks(List<TaskDto> tasks) {
    this.tasks = tasks;
  }

}
