package life.coachy.backend.conversation.domain;

import java.util.Optional;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.conversation.query.ConversationQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    conversation.setRecipientName(queryDto.getRecipientName());
    conversation.setSenderName(queryDto.getSenderName());

    this.save(conversation);
  }

  ConversationQueryDto findOneOrThrow(ObjectId id) {
    return this.findOne(id).orElseThrow(ConversationNotFoundException::new);
  }

  void findOneOrCreate(ObjectId id, Conversation conversation, Runnable runnable) {
    Optional<ConversationQueryDto> queryDto = this.findOne(id);

    if (!queryDto.isPresent()) {
      this.save(conversation);
      runnable.run();
    }
  }

  Page<ConversationQueryDto> findAllByRecipientOrSender(ObjectId id, Pageable pageable) {
    return this.queryRepository.findAllByRecipientIdOrSenderIdOrderByLastMessageDateDesc(id, id, pageable);
  }

  private Optional<ConversationQueryDto> findOne(ObjectId id) {
    return this.queryRepository.findById(id);
  }

}
