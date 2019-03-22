package life.coachy.backend.board.task.dto;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class TaskDtoBuilder implements Buildable<TaskDto> {

  ObjectId identifier;
  String name;
  String color;
  String content;

  private TaskDtoBuilder() {}

  public static TaskDtoBuilder createBuilder() { return new TaskDtoBuilder(); }

  public TaskDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public TaskDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public TaskDtoBuilder withColor(String color) {
    this.color = color;
    return this;
  }

  public TaskDtoBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  @Override
  public TaskDto build() {
    return new TaskDto(this);
  }

}
