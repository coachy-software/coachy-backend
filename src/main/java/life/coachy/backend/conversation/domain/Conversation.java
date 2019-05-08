package life.coachy.backend.conversation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.CONVERSATIONS)
class Conversation {

  @Id private ObjectId identifier;
  private String senderName;
  private String recipientName;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  Conversation() {}

  Conversation(ConversationBuilder builder) {
    this.identifier = builder.identifier;
    this.senderName = builder.senderName;
    this.recipientName = builder.recipientName;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

}
