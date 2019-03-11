package life.coachy.backend.board.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardUpdateDto extends AbstractDto {

  @NotNull private String name;
  @NotNull private List<LabelDto> label;
  @NotNull private ObjectId ownerId;

  BoardUpdateDto() {}

  BoardUpdateDto(BoardUpdateDtoBuilder builder) {
    this.name = builder.name;
    this.label = builder.label;
    this.ownerId = builder.owner;
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

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

  public void setOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }


}
