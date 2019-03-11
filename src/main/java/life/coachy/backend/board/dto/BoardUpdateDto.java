package life.coachy.backend.board.dto;

import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardUpdateDto extends AbstractDto {

  @NotNull private String name;
  @NotNull private LabelDto label;
  @NotNull private UserDto owner;

  BoardUpdateDto() {}

  BoardUpdateDto(BoardUpdateDtoBuilder builder) {
    this.name = builder.name;
    this.label = builder.label;
    this.owner = builder.owner;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LabelDto getLabel() {
    return this.label;
  }

  public void setLabel(LabelDto label) {
    this.label = label;
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
