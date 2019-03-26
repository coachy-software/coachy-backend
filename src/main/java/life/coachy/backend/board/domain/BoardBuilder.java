package life.coachy.backend.board.domain;

import java.util.List;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class BoardBuilder implements Buildable<Board> {

  ObjectId identifier;
  String name;
  List<LabelDto> labels;
  ObjectId ownerId;

  private BoardBuilder() {}

  public static BoardBuilder create() {
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

  public BoardBuilder withLabels(List<LabelDto> labels) {
    this.labels = labels;
    return this;
  }

  public BoardBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  @Override
  public Board build() {
    return new Board(this);
  }

}
