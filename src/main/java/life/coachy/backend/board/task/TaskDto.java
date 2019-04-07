package life.coachy.backend.board.task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.bson.types.ObjectId;

public class TaskDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull private String name;
  @Pattern(regexp = "(?i)^((0x)?|#?)([0-9A-F]{8}|[0-9A-F]{6})$") private String color;
  private String content;

  public TaskDto(ObjectId identifier, String name, String color, String content) {
    this.identifier = identifier;
    this.name = name;
    this.color = color;
    this.content = content;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public String getColor() {
    return this.color;
  }

  public String getContent() {
    return this.content;
  }

}
