package life.coachy.backend.conversation.message.domain;

import life.coachy.backend.conversation.message.domain.dto.OutputMessageDto;

class MessageCreator {

  Message from(OutputMessageDto dto) {
    return MessageBuilder.create()
        .withBody(dto.getBody())
        .withConversationId(dto.getConversationId())
        .build();
  }

}
