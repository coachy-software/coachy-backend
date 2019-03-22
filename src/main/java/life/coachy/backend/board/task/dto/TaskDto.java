package life.coachy.backend.board.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.infrastructure.util.dto.AbstractDto;
import org.bson.types.ObjectId;

public class TaskDto extends AbstractDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull private String name;
  @Pattern(regexp = "(?i)^((0x)?|#?)([0-9A-F]{8}|[0-9A-F]{6})$") private String color;
  private String content;

  TaskDto() {}

  TaskDto(TaskDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.color = builder.color;
    this.content = builder.content;
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
  public String getEntityName() {
    return this.name;
  }

}
