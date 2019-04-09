package life.coachy.backend.board.domain.dto;

import java.util.LinkedHashSet;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class BoardUpdateCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private LinkedHashSet<LabelDto> labels;

  public BoardUpdateCommandDto(String name, LinkedHashSet<LabelDto> labels) {
    this.name = name;
    this.labels = labels;
  }

  public String getName() {
    return this.name;
  }

  public LinkedHashSet<LabelDto> getLabels() {
    return this.labels;
  }

}
