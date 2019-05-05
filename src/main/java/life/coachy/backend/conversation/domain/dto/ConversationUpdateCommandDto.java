package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public class ConversationUpdateCommandDto {

  private ObjectId identifier;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  ConversationUpdateCommandDto(ConversationUpdateCommandDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
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
