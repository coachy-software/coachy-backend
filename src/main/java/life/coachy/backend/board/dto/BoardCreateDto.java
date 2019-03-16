package life.coachy.backend.board.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardCreateDto extends AbstractDto {

  @NotNull private String name;
  @NotNull private List<LabelDto> labels;
  @NotNull private UserDto owner;

  BoardCreateDto() {}

  public BoardCreateDto(String name, List<LabelDto> labels, UserDto owner) {
    this.name = name;
    this.labels = labels;
    this.owner = owner;
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

  public UserDto getOwner() {
    return this.owner;
  }

  public void setOwner(UserDto owner) {
    this.owner = owner;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
