package life.coachy.backend.conversation.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.CONVERSATIONS)
public class ConversationQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId senderId;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId recipientId;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getSenderId() {
    return this.senderId;
  }

  public ObjectId getRecipientId() {
    return this.recipientId;
  }

  public ObjectId getLastMessageId() {
    return this.lastMessageId;
  }

  public String getLastMessageText() {
    return this.lastMessageText;
  }

  public LocalDateTime getLastMessageDate() {
    return this.lastMessageDate;
  }

}
