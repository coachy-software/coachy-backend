package life.coachy.backend.conversation.message

import groovy.transform.CompileStatic
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDto
import life.coachy.backend.conversation.message.domain.dto.OutputMessageDtoBuilder
import org.bson.types.ObjectId

import java.time.LocalDateTime

@CompileStatic
trait SampleMessages {

  ObjectId sampleMessageId = ObjectId.get()

  OutputMessageDto sampleOutputMessageDto = OutputMessageDtoBuilder.create()
      .withIdentifier(sampleMessageId)
      .withFrom("yang160")
      .withBody("test message")
      .withConversationId(ObjectId.get())
      .withDate(LocalDateTime.now())
      .build()

}
