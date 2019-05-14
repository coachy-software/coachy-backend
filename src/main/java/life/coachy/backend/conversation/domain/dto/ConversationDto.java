package life.coachy.backend.conversation.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;

public class ConversationDto {

  private ObjectId identifier;
  private List<String> conversers;
  private ObjectId lastMessageId;
  private String lastMessageText;
  private LocalDateTime lastMessageDate;

  ConversationDto(ConversationDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.conversers = builder.conversers;
    this.lastMessageId = builder.lastMessageId;
    this.lastMessageText = builder.lastMessageText;
    this.lastMessageDate = builder.lastMessageDate;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public List<String> getConversers() {
    return this.conversers;
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
