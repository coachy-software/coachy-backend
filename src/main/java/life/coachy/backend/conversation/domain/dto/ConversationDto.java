package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class ConversationDto {

  private ObjectId senderId;
  private ObjectId recipientId;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  ConversationDto(ConversationDtoBuilder builder) {
    this.senderId = builder.senderId;
    this.recipientId = builder.recipientId;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
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
