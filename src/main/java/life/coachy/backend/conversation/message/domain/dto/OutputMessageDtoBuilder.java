package life.coachy.backend.conversation.message.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class OutputMessageDtoBuilder implements Buildable<OutputMessageDto> {

  ObjectId identifier;
  String from;
  String body;
  ObjectId conversationId;
  LocalDateTime date;

  private OutputMessageDtoBuilder() {}

  public static OutputMessageDtoBuilder create() {
    return new OutputMessageDtoBuilder();
  }

  public OutputMessageDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public OutputMessageDtoBuilder withFrom(String from) {
    this.from = from;
    return this;
  }

  public OutputMessageDtoBuilder withBody(String body) {
    this.body = body;
    return this;
  }

  public OutputMessageDtoBuilder withConversationId(ObjectId conversationId) {
    this.conversationId = conversationId;
    return this;
  }

  public OutputMessageDtoBuilder withDate(LocalDateTime date) {
    this.date = date;
    return this;
  }

  @Override
  public OutputMessageDto build() {
    return new OutputMessageDto(this);
  }

}
