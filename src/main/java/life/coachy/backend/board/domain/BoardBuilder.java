package life.coachy.backend.board.domain;

import java.util.LinkedHashSet;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class BoardBuilder implements Buildable<Board> {

  ObjectId identifier;
  String name;
  LinkedHashSet<LabelDto> labels;
  ObjectId ownerId;

  private BoardBuilder() {}

  public static BoardBuilder create() {
    return new BoardBuilder();
  }

  BoardBuilder withName(String name) {
    this.name = name;
    return this;
  }

  BoardBuilder withLabels(LinkedHashSet<LabelDto> labels) {
    this.labels = labels;
    return this;
  }

  BoardBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  @Override
  public Board build() {
    return new Board(this);
  }

}
