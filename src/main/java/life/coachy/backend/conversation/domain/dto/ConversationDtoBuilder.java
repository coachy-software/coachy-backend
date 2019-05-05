package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ConversationDtoBuilder implements Buildable<ConversationDto> {

  ObjectId senderId;
  ObjectId recipientId;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationDtoBuilder() {}

  public static ConversationDtoBuilder create() {
    return new ConversationDtoBuilder();
  }

  public ConversationDtoBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  public ConversationDtoBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
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
