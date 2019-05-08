package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ConversationDtoBuilder implements Buildable<ConversationDto> {

  ObjectId identifier;
  String senderName;
  String recipientName;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationDtoBuilder() {}

  public static ConversationDtoBuilder create() {
    return new ConversationDtoBuilder();
  }

  public ConversationDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ConversationDtoBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  public ConversationDtoBuilder withRecipientName(String recipientName) {
    this.recipientName = recipientName;
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
