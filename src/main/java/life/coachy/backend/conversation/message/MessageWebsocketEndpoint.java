package life.coachy.backend.conversation.message;

import java.time.LocalDateTime;
import life.coachy.backend.conversation.domain.ConversationFacade;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDtoBuilder;
import life.coachy.backend.conversation.message.domain.MessageFacade;
import life.coachy.backend.conversation.message.domain.dto.InputMessageDto;
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDto;
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDtoBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
class MessageWebsocketEndpoint {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ConversationFacade conversationFacade;
  private final MessageFacade messageFacade;

  @Autowired
  public MessageWebsocketEndpoint(SimpMessagingTemplate simpMessagingTemplate, ConversationFacade conversationFacade, MessageFacade messageFacade) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.conversationFacade = conversationFacade;
    this.messageFacade = messageFacade;
  }

  @MessageMapping("/chat.message.private")
  void chatMessagePrivate(InputMessageDto inputMessage) {
    OutputMessageDto outputMessage = this.createMessageDto(inputMessage);
    this.conversationFacade.updateLastMesasge(inputMessage.getConversationId(), this.createConversationUpdateDto(outputMessage));

    this.simpMessagingTemplate.convertAndSendToUser(inputMessage.getTo(), "/queue/private", outputMessage);
    this.messageFacade.save(outputMessage);
  }

  @MessageMapping("/chat.message.typing")
  void chatTyping(InputMessageDto dto) {
    OutputMessageDto outputMessage = OutputMessageDtoBuilder.create()
        .withFrom(dto.getFrom())
        .withConversationId(dto.getConversationId())
        .build();

    this.simpMessagingTemplate.convertAndSendToUser(dto.getTo(), "/queue/typing", outputMessage);
  }

  private ConversationUpdateCommandDto createConversationUpdateDto(OutputMessageDto outputMessage) {
    return ConversationUpdateCommandDtoBuilder.create()
        .withLastMessageDate(outputMessage.getDate())
        .withLastMessageText(outputMessage.getBody())
        .withLastMessageId(outputMessage.getIdentifier())
        .build();
  }

  private OutputMessageDto createMessageDto(InputMessageDto dto) {
    return OutputMessageDtoBuilder.create()
        .withConversationId(dto.getConversationId())
        .withFrom(dto.getFrom())
        .withBody(dto.getBody())
        .withDate(LocalDateTime.now())
        .withIdentifier(ObjectId.get())
        .build();
  }

}
