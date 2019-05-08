package life.coachy.backend.conversation

import groovy.transform.CompileStatic
import life.coachy.backend.conversation.domain.dto.ConversationDto
import life.coachy.backend.conversation.domain.dto.ConversationDtoBuilder
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDtoBuilder
import org.bson.types.ObjectId

import java.time.LocalDateTime

@CompileStatic
trait SampleConversations {

  ObjectId sampleConversationId = ObjectId.get()

  ConversationDto sampleConversationDto = ConversationDtoBuilder.create()
      .withIdentifier(sampleConversationId)
      .withSenderName("yang160")
      .withRecipientName("yang160")
      .withLastMessageDate(LocalDateTime.now())
      .withLastMessageId(sampleConversationId)
      .withLastMessageText("test")
      .build();

  ConversationUpdateCommandDto sampleConversationUpdateCommandDto = ConversationUpdateCommandDtoBuilder.create()
      .withIdentifier(sampleConversationId)
      .withLastMessageDate(LocalDateTime.now())
      .withLastMessageId(sampleConversationId)
      .withLastMessageText("edited")
      .build()
}
