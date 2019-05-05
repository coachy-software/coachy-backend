package life.coachy.backend.conversation.domain;

import java.util.Set;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.conversation.query.ConversationQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ConversationService {

  private final ConversationRepository repository;
  private final ConversationQueryRepository queryRepository;

  @Autowired
  ConversationService(ConversationRepository repository, ConversationQueryRepository queryRepository) {
    this.repository = repository;
    this.queryRepository = queryRepository;
  }

  void save(Conversation conversation) {
    this.repository.save(conversation);
  }

  void update(ConversationQueryDto queryDto, Conversation conversation) {
    conversation.setRecipientId(queryDto.getRecipientId());
    conversation.setSenderId(queryDto.getSenderId());

    this.save(conversation);
  }

  ConversationQueryDto findOneOrThrow(ObjectId id) {
    return this.queryRepository.findById(id).orElseThrow(ConversationNotFoundException::new);
  }

  Set<ConversationQueryDto> findAllByRecipientOrSender(ObjectId id) {
    return this.queryRepository.findAllByRecipientIdOrSenderIdOrderByLastMessageDateDesc(id, id);
  }

}
