package life.coachy.backend.conversation.message.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class OutputMessageDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String from;
  private String body;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId conversationId;
  private LocalDateTime date;

  OutputMessageDto() {}

  OutputMessageDto(OutputMessageDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.from = builder.from;
    this.body = builder.body;
    this.conversationId = builder.conversationId;
    this.date = builder.date;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getFrom() {
    return this.from;
  }

  public ObjectId getConversationId() {
    return this.conversationId;
  }

  public String getBody() {
    return this.body;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

}
