package life.coachy.backend.board;

import java.util.List;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

final class BoardBuilder implements Buildable<Board> {

  ObjectId identifier;
  String name;
  List<LabelDto> label;
  UserDto owner;

  private BoardBuilder() {}

  public static BoardBuilder createBuilder() {
    return new BoardBuilder();
  }

  public BoardBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public BoardBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public BoardBuilder withLabel(List<LabelDto> label) {
    this.label = label;
    return this;
  }

  public BoardBuilder withOwner(UserDto owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public Board build() {
    return new Board(this);
  }

}
