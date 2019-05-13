package life.coachy.backend.conversation.message.domain;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class MessageBuilder implements Buildable<Message> {

  ObjectId identifier;
  ObjectId conversationId;
  String senderName;
  String body;

  private MessageBuilder() {}

  public static MessageBuilder create() {
    return new MessageBuilder();
  }

  MessageBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  MessageBuilder withConversationId(ObjectId conversationId) {
    this.conversationId = conversationId;
    return this;
  }

  MessageBuilder withSenderName(String senderName) {
    this.senderName = senderName;
    return this;
  }

  MessageBuilder withBody(String body) {
    this.body = body;
    return this;
  }

  @Override
  public Message build() {
    return new Message(this);
  }

}
