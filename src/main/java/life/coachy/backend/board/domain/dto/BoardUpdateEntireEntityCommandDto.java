package life.coachy.backend.board.domain.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class BoardUpdateEntireEntityCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String name;
  @NotNull private List<LabelDto> labels;

  public BoardUpdateEntireEntityCommandDto(String name, List<LabelDto> labels) {
    this.name = name;
    this.labels = labels;
  }

  public String getName() {
    return this.name;
  }

  public List<LabelDto> getLabels() {
    return this.labels;
  }

}
