package life.coachy.backend.board.dto;

import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;

public class BoardUpdateDtoBuilder implements Buildable<BoardUpdateDto> {

  String name;
  LabelDto label;
  UserDto owner;

  private BoardUpdateDtoBuilder() {}

  public static BoardUpdateDtoBuilder createBuilder() {
    return new BoardUpdateDtoBuilder();
  }

  public BoardUpdateDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public BoardUpdateDtoBuilder withLabel(LabelDto label) {
    this.label = label;
    return this;
  }

  public BoardUpdateDtoBuilder withOwner(UserDto owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public BoardUpdateDto build() {
    return new BoardUpdateDto(this);
  }

}
