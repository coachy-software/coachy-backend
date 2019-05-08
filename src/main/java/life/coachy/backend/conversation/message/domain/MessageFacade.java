package life.coachy.backend.conversation.message.domain;

import java.util.Set;
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDto;
import life.coachy.backend.conversation.message.query.MessageQueryDto;
import org.bson.types.ObjectId;

public class MessageFacade {

  private final MessageService messageService;
  private final MessageCreator messageCreator;

  MessageFacade(MessageService messageService, MessageCreator messageCreator) {
    this.messageService = messageService;
    this.messageCreator = messageCreator;
  }

  public void save(OutputMessageDto outputMessageDto) {
    this.messageService.save(this.messageCreator.from(outputMessageDto));
  }

  public Set<MessageQueryDto> findAllByConversationId(ObjectId conversationId) {
    return this.messageService.findAllByConversationId(conversationId);
  }

}

