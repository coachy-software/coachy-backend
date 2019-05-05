package life.coachy.backend.conversation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.CONVERSATIONS)
class Conversation {

  @Id private ObjectId identifier;
  private ObjectId senderId;
  private ObjectId recipientId;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  Conversation() {}

  Conversation(ConversationBuilder builder) {
    this.identifier = builder.identifier;
    this.senderId = builder.senderId;
    this.recipientId = builder.recipientId;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  public void setSenderId(ObjectId senderId) {
    this.senderId = senderId;
  }

  public void setRecipientId(ObjectId recipientId) {
    this.recipientId = recipientId;
  }

}
