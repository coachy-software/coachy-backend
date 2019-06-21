package life.coachy.backend.conversation

import com.google.common.collect.Lists
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

  ConversationDto sampleConversers = ConversationDtoBuilder.create()
      .withConversers(Lists.newArrayList("yang160", "yang160"))
      .build();

  ConversationDto sampleConversationDto = ConversationDtoBuilder.create()
      .withConversers(Lists.newArrayList("yang160", "yang160"))
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
