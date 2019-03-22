package life.coachy.backend.board.task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Task implements IdentifiableEntity<ObjectId> {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId identifier;
  private String name;
  private String color;
  private String content;

  Task() {}

  Task(TaskBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.color = builder.color;
    this.content = builder.content;
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

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Task{" +
        "identifier=" + this.identifier +
        ", name='" + this.name + '\'' +
        ", color='" + this.color + '\'' +
        ", content='" + this.content + '\'' +
        '}';
  }

}
