package life.coachy.backend.conversation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class ConversationBuilder implements Buildable<Conversation> {

  ObjectId identifier;
  ObjectId senderId;
  ObjectId recipientId;
  ObjectId lastMessageId;
  String lastMessageText;
  LocalDateTime lastMessageDate;

  private ConversationBuilder() {}

  public static ConversationBuilder create() {
    return new ConversationBuilder();
  }

  public ConversationBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ConversationBuilder withSenderId(ObjectId senderId) {
    this.senderId = senderId;
    return this;
  }

  public ConversationBuilder withRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
    return this;
  }

  public ConversationBuilder withLastMessageId(ObjectId lastMessageId) {
    this.lastMessageId = lastMessageId;
    return this;
  }

  public ConversationBuilder withLastMessageText(String lastMessageText) {
    this.lastMessageText = lastMessageText;
    return this;
  }

  public ConversationBuilder withLastMessageDate(LocalDateTime lastMessageDate) {
    this.lastMessageDate = lastMessageDate;
    return this;
  }

  @Override
  public Conversation build() {
    return new Conversation(this);
  }

}
