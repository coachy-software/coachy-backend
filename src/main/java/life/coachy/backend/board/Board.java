package life.coachy.backend.board;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("boards")
class Board implements IdentifiableEntity<ObjectId> {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId identifier;
  private String name;
  private List<LabelDto> labels;
//  private UserDto owner;

  Board() {}

  Board(BoardBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.labels = builder.label;
//    this.owner = builder.owner;
  }

  @Override
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

  public List<LabelDto> getLabels() {
    return this.labels;
  }

  public void setLabels(List<LabelDto> labels) {
    this.labels = labels;
  }

//  public UserDto getOwner() {
//    return this.owner;
//  }
//
//  public void setOwner(UserDto owner) {
//    this.owner = owner;
//  }

  // todo

  @Override
  public String toString() {
    return "Board{" +
        "identifier=" + this.identifier +
        ", name='" + this.name + '\'' +
        ", labels=" + this.labels +
//        ", owner=" + this.owner +
        '}';
  }

}
