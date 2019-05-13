package life.coachy.backend.conversation.message;

import java.time.LocalDateTime;
import life.coachy.backend.conversation.domain.ConversationFacade;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.dto.ConversationDtoBuilder;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDtoBuilder;
import life.coachy.backend.conversation.message.domain.MessageFacade;
import life.coachy.backend.conversation.message.domain.dto.InputMessageDto;
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDto;
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDtoBuilder;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
class MessageWebsocketEndpoints {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ConversationFacade conversationFacade;
  private final MessageFacade messageFacade;

  @Autowired
  public MessageWebsocketEndpoints(SimpMessagingTemplate simpMessagingTemplate, ConversationFacade conversationFacade, MessageFacade messageFacade) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.conversationFacade = conversationFacade;
    this.messageFacade = messageFacade;
  }

  @MessageMapping("/chat.message.private")
  void chatMessagePrivate(InputMessageDto dto) {
    OutputMessageDto outputMessage = this.createMessageDto(dto).build();

    ConversationQueryDto queryDto = this.updateConversationLastMessage(outputMessage, dto);
    outputMessage = this.createMessageDto(dto).withConversationId(queryDto.getIdentifier()).build();

    this.simpMessagingTemplate.convertAndSendToUser(dto.getTo(), "/queue/private", outputMessage);
    this.messageFacade.save(outputMessage);
  }

  private ConversationQueryDto updateConversationLastMessage(OutputMessageDto outputMessage, InputMessageDto dto) {
    ConversationDto conversationDto = this.createConversationDto(outputMessage, dto);

    ConversationQueryDto queryDto = this.conversationFacade.createIfAbsent(conversationDto);
    this.conversationFacade.updateLastMesasge(conversationDto, this.createConversationUpdateDto(outputMessage));

    return queryDto;
  }

  private ConversationUpdateCommandDto createConversationUpdateDto(OutputMessageDto outputMessage) {
    return ConversationUpdateCommandDtoBuilder.create()
        .withLastMessageDate(outputMessage.getDate())
        .withLastMessageText(outputMessage.getBody())
        .withLastMessageId(outputMessage.getIdentifier())
        .build();
  }

  private OutputMessageDtoBuilder createMessageDto(InputMessageDto dto) {
    return OutputMessageDtoBuilder.create()
        .withFrom(dto.getFrom())
        .withBody(dto.getBody())
        .withDate(LocalDateTime.now())
        .withIdentifier(ObjectId.get());
  }

  private ConversationDto createConversationDto(OutputMessageDto outputMessage, InputMessageDto inputMessageDto) {
    return ConversationDtoBuilder.create()
        .withLastMessageDate(outputMessage.getDate())
        .withLastMessageId(outputMessage.getIdentifier())
        .withLastMessageText(outputMessage.getBody())
        .withSenderName(inputMessageDto.getFrom())
        .withRecipientName(inputMessageDto.getTo())
        .build();
  }

}
