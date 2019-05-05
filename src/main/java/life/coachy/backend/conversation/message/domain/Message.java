package life.coachy.backend.conversation.message.domain;

import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.MESSAGES)
class Message {

  private ObjectId identifier;
  private ObjectId conversationId;
  private String body;

  Message() {}

}
