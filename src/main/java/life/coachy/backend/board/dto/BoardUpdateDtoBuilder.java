package life.coachy.backend.board.dto;

import java.util.List;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public class BoardUpdateDtoBuilder implements Buildable<BoardUpdateDto> {

  String name;
  List<LabelDto> label;
  ObjectId owner;

  private BoardUpdateDtoBuilder() {}

  public static BoardUpdateDtoBuilder createBuilder() {
    return new BoardUpdateDtoBuilder();
  }

  public BoardUpdateDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public BoardUpdateDtoBuilder withLabel(List<LabelDto> label) {
    this.label = label;
    return this;
  }

  public BoardUpdateDtoBuilder withOwnerId(ObjectId owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public BoardUpdateDto build() {
    return new BoardUpdateDto(this);
  }

}
