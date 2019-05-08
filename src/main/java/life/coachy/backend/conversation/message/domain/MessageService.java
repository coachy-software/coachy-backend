package life.coachy.backend.conversation.message.domain;

import java.util.Set;
import life.coachy.backend.conversation.message.domain.exception.MessageNotFoundException;
import life.coachy.backend.conversation.message.query.MessageQueryDto;
import life.coachy.backend.conversation.message.query.MessageQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MessageService {

  private final MessageRepository messageRepository;
  private final MessageQueryRepository messageQueryRepository;

  @Autowired
  MessageService(MessageRepository messageRepository, MessageQueryRepository messageQueryRepository) {
    this.messageRepository = messageRepository;
    this.messageQueryRepository = messageQueryRepository;
  }

  void save(Message message) {
    this.messageRepository.save(message);
  }

  Set<MessageQueryDto> findAllByConversationId(ObjectId id) {
    return this.messageQueryRepository.findAllByConversationId(id).orElseThrow(MessageNotFoundException::new);
  }

}
