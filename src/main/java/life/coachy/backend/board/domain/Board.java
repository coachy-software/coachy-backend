package life.coachy.backend.board.domain;

import java.util.LinkedHashSet;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.BOARDS)
class Board {

  @Id ObjectId identifier;
  private String name;
  private LinkedHashSet<LabelDto> labels;
  private ObjectId ownerId;

  Board() {}

  Board(BoardBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.labels = builder.labels;
    this.ownerId = builder.ownerId;
  }

  public static BoardBuilder builder() {
    return BoardBuilder.create();
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  public void setOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
  }

}
