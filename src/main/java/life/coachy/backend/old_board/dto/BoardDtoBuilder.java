package life.coachy.backend.old_board.dto;

import java.util.List;
import life.coachy.backend.old_board.label.dto.LabelDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class BoardDtoBuilder implements Buildable<BoardDto> {

  ObjectId identifier;
  String name;
  List<LabelDto> label;
  ObjectId owner;

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

  public BoardDtoBuilder withLabel(List<LabelDto> label) {
    this.label = label;
    return this;
  }

  public BoardDtoBuilder withOwner(ObjectId owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public BoardDto build() {
    return new BoardDto(this);
  }

}
