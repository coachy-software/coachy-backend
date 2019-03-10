package life.coachy.backend.board;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("boards")
class Board implements IdentifiableEntity<ObjectId> {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId identifier;
  private String name;
  private LabelDto label;
  private UserDto owner;

  Board() {}

  Board(BoardBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.label = builder.label;
    this.owner = builder.owner;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
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
  public String toString() {
    return "Board{" +
        "identifier=" + this.identifier +
        ", name='" + this.name + '\'' +
        ", label=" + this.label +
        ", owner=" + this.owner +
        '}';
  }

}
