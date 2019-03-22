package life.coachy.backend.board.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import javax.validation.constraints.NotNull;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.infrastructure.util.dto.AbstractDto;
import life.coachy.backend.infrastructure.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = BoardMapper.class, entityName = "Board")
public class BoardCreateDto extends AbstractDto {

  @NotNull private String name;
  @NotNull private List<LabelDto> labels;
  @JsonSerialize(using = ToStringSerializer.class) @NotNull private ObjectId owner;

  BoardCreateDto() {}

  public BoardCreateDto(String name, List<LabelDto> labels, ObjectId owner) {
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

  public ObjectId getOwner() {
    return this.owner;
  }

  public void setOwner(ObjectId owner) {
    this.owner = owner;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
