package life.coachy.backend.board.task;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Task {

  @Id private ObjectId identifier;
  private String name;
  private String color;
  private String content;

  Task() {}

  Task(ObjectId identifier, String name, String color, String content) {
    this.identifier = identifier;
    this.name = name;
    this.color = color;
    this.content = content;
  }

}
