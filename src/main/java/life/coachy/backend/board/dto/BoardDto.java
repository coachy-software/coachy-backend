package life.coachy.backend.board.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardDto extends AbstractDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull private String name;
  @NotNull private List<LabelDto> label;
  @NotNull private UserDto owner;

  BoardDto() {}

  BoardDto(BoardDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.label = builder.label;
    this.owner = builder.owner;
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

  public List<LabelDto> getLabel() {
    return this.label;
  }

  public void setLabel(List<LabelDto> label) {
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