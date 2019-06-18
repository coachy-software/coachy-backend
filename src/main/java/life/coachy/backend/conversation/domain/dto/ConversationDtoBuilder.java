package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ConversationDtoBuilder implements Buildable<ConversationDto> {

  List<String> conversers;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationDtoBuilder() {}

  public static ConversationDtoBuilder create() {
    return new ConversationDtoBuilder();
  }

  public ConversationDtoBuilder withConversers(List<String> conversers) {
    this.conversers = conversers;
    return this;
  }

  public ConversationDtoBuilder withLastMessageId(ObjectId lastMessageId) {
    this.lastMessageId = lastMessageId;
    return this;
  }

  public ConversationDtoBuilder withLastMessageText(String lastMessageText) {
    this.lastMessageText = lastMessageText;
    return this;
  }

  public ConversationDtoBuilder withLastMessageDate(LocalDateTime lastMessageDate) {
    this.lastMessageDate = lastMessageDate;
    return this;
  }

  @Override
  public ConversationDto build() {
    return new ConversationDto(this);
  }

}
