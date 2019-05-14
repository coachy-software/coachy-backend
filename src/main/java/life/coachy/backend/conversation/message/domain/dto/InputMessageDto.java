package life.coachy.backend.conversation.message.domain.dto;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class InputMessageDto {

  private String from;
  private String to;
  private String body;
  private LocalDateTime date;
  private ObjectId conversationId;

  InputMessageDto() {}

  public InputMessageDto(String from, String to, String body, LocalDateTime date, ObjectId conversationId) {
    this.from = from;
    this.to = to;
    this.body = body;
    this.date = date;
    this.conversationId = conversationId;
  }

  public String getFrom() {
    return this.from;
  }

  public String getTo() {
    return this.to;
  }

  public String getBody() {
    return this.body;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public ObjectId getConversationId() {
    return this.conversationId;
  }

}
