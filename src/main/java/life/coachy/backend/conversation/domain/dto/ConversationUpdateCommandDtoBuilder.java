package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ConversationUpdateCommandDtoBuilder implements Buildable<ConversationUpdateCommandDto> {

  ObjectId identifier;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationUpdateCommandDtoBuilder() {}

  public static ConversationUpdateCommandDtoBuilder create() {
    return new ConversationUpdateCommandDtoBuilder();
  }

  public ConversationUpdateCommandDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ConversationUpdateCommandDtoBuilder withLastMessageId(ObjectId lastMessageId) {
    this.lastMessageId = lastMessageId;
    return this;
  }

  public ConversationUpdateCommandDtoBuilder withLastMessageText(String lastMessageText) {
    this.lastMessageText = lastMessageText;
    return this;
  }

  public ConversationUpdateCommandDtoBuilder withLastMessageDate(LocalDateTime lastMessageDate) {
    this.lastMessageDate = lastMessageDate;
    return this;
  }

  @Override
  public ConversationUpdateCommandDto build() {
    return new ConversationUpdateCommandDto(this);
  }

}
