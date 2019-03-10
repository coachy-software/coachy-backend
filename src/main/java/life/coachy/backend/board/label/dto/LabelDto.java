package life.coachy.backend.board.label.dto;

import java.util.List;
import life.coachy.backend.board.task.dto.TaskDto;
import org.bson.types.ObjectId;

public class LabelDto {

  private ObjectId identifier;
  private String name;
  private List<TaskDto> tasks;

  LabelDto() {}

  public LabelDto(ObjectId identifier, String name, List<TaskDto> tasks) {
    this.identifier = identifier;
    this.name = name;
    this.tasks = tasks;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
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
