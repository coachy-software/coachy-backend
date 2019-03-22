package life.coachy.backend.board.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.infrastructure.util.dto.AbstractDto;
import life.coachy.backend.infrastructure.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardUpdateDto extends AbstractDto {

  @NotNull private String name;
  @NotNull private List<LabelDto> labels;

  BoardUpdateDto() {}

  public BoardUpdateDto(String name, List<LabelDto> labels) {
    this.name = name;
    this.labels = labels;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<LabelDto> getLabels() {
    return this.labels;
  }

  public void setLabels(List<LabelDto> labels) {
    this.labels = labels;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }


}
