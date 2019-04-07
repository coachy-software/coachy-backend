package life.coachy.backend.board.domain.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import org.bson.types.ObjectId;

public class BoardCreateCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private List<LabelDto> labels;
  @NotNull private ObjectId ownerId;

  public BoardCreateCommandDto(String name, List<LabelDto> labels, ObjectId ownerId) {
    this.name = name;
    this.labels = labels;
    this.ownerId = ownerId;
  }

  public String getName() {
    return this.name;
  }

  public List<LabelDto> getLabels() {
    return this.labels;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

}
