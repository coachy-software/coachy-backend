package life.coachy.backend.conversation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class ConversationBuilder implements Buildable<Conversation> {

  ObjectId identifier;
  String senderName;
  String recipientName;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationBuilder() {}

  public static ConversationBuilder create() {
    return new ConversationBuilder();
  }

  ConversationBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  ConversationBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  ConversationBuilder withRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  ConversationBuilder withLastMessageId(ObjectId lastMessageId) {
    this.lastMessageId = lastMessageId;
    return this;
  }

  ConversationBuilder withLastMessageText(String lastMessageText) {
    this.lastMessageText = lastMessageText;
    return this;
  }

  ConversationBuilder withLastMessageDate(LocalDateTime lastMessageDate) {
    this.lastMessageDate = lastMessageDate;
    return this;
  }

  @Override
  public Conversation build() {
    return new Conversation(this);
  }

}
