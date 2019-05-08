package life.coachy.backend.conversation.domain;

import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;

class ConversationCreator {

  Conversation from(ConversationDto dto) {
    return ConversationBuilder.create()
        .withIdentifier(dto.getIdentifier())
        .withSenderName(dto.getSenderName())
        .withRecipientName(dto.getRecipientName())
        .withLastMessageId(dto.getLastMessageId())
        .withLastMessageText(dto.getLastMessageText())
        .withLastMessageDate(dto.getLastMessageDate())
        .build();
  }

  Conversation from(ConversationUpdateCommandDto dto) {
    return ConversationBuilder.create()
        .withIdentifier(dto.getIdentifier())
        .withLastMessageId(dto.getLastMessageId())
        .withLastMessageText(dto.getLastMessageText())
        .withLastMessageDate(dto.getLastMessageDate())
        .build();
  }


}
