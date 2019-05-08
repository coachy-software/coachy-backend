package life.coachy.backend.conversation.message.domain;

import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.MESSAGES)
class Message {

  @Id private ObjectId identifier;
  private ObjectId conversationId;
  private String body;

  Message() {}

  Message(MessageBuilder builder) {
    this.identifier = builder.identifier;
    this.conversationId = builder.conversationId;
    this.body = builder.body;
  }

}
