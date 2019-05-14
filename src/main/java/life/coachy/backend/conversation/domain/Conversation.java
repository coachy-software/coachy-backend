package life.coachy.backend.conversation.domain;

import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.CONVERSATIONS)
class Conversation {

  @Id private ObjectId identifier;
  private List<String> conversers;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  Conversation() {}

  Conversation(ConversationBuilder builder) {
    this.identifier = builder.identifier;
    this.conversers = builder.conversers;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  void setConversers(List<String> conversers) {
    this.conversers = conversers;
  }

}
