package life.coachy.backend.board.dto;

import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public final class BoardDtoBuilder implements Buildable<BoardDto> {

  ObjectId identifier;
  String name;
  LabelDto label;
  UserDto owner;

  private BoardDtoBuilder() {}

  public static BoardDtoBuilder createBuilder() {
    return new BoardDtoBuilder();
  }

  public BoardDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public BoardDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public BoardDtoBuilder withLabel(LabelDto label) {
    this.label = label;
    return this;
  }

  public BoardDtoBuilder withOwner(UserDto owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public BoardDto build() {
    return new BoardDto(this);
  }

}
