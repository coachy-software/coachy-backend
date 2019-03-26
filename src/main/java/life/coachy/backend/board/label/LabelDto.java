package life.coachy.backend.board.label;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.task.TaskDto;
import org.bson.types.ObjectId;

public class LabelDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull private String name;
  @NotNull private List<TaskDto> tasks;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public List<TaskDto> getTasks() {
    return this.tasks;
  }

}
