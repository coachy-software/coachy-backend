package life.coachy.backend.conversation.message.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.MESSAGES)
public class MessageQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId conversationId;
  private String senderName;
  private String body;

  MessageQueryDto() {}

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getConversationId() {
    return this.conversationId;
  }

  public String getSenderName() {
    return this.senderName;
  }

  public String getBody() {
    return this.body;
  }

}
