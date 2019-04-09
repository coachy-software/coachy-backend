package life.coachy.backend.board.label;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.LinkedHashSet;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.task.TaskDto;
import org.bson.types.ObjectId;

public class LabelDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull private String name;
  @NotNull private LinkedHashSet<TaskDto> tasks;

  public LabelDto(ObjectId identifier, String name, LinkedHashSet<TaskDto> tasks) {
    this.identifier = identifier;
    this.name = name;
    this.tasks = tasks;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public LinkedHashSet<TaskDto> getTasks() {
    return this.tasks;
  }

}
