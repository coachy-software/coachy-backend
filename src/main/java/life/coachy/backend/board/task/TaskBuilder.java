package life.coachy.backend.board.task;

import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

final class TaskBuilder implements Buildable<Task> {

  ObjectId identifier;
  String name;
  String color;
  String content;

  private TaskBuilder() {}

  public static TaskBuilder createBuilder() {
    return new TaskBuilder();
  }

  public TaskBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public TaskBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public TaskBuilder withColor(String color) {
    this.color = color;
    return this;
  }

  public TaskBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  @Override
  public Task build() {
    return new Task(this);
  }

}
