package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class ConversationDto {

  private ObjectId identifier;
  private String senderName;
  private String recipientName;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  ConversationDto(ConversationDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.senderName = builder.senderName;
    this.recipientName = builder.recipientName;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getSenderName() {
    return this.senderName;
  }

  public String getRecipientName() {
    return this.recipientName;
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
