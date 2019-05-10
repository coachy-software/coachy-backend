package life.coachy.backend.conversation.domain;

import java.util.Optional;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
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
    conversation.setIdentifier(queryDto.getIdentifier());
    conversation.setRecipientName(queryDto.getRecipientName());
    conversation.setSenderName(queryDto.getSenderName());

    this.save(conversation);
  }

  ConversationQueryDto findOneOrThrow(String recipientName, String senderName) {
    return this.findOne(recipientName, senderName).orElseThrow(ConversationNotFoundException::new);
  }

  void createIfAbsent(ConversationDto dto, Conversation conversation, Runnable runnable) {
    if (!this.queryRepository.existsByRecipientNameAndSenderName(dto.getRecipientName(), dto.getSenderName())) {
      this.save(conversation);
      runnable.run();
    }
  }

  Page<ConversationQueryDto> findAllByRecipientOrSender(String username, Pageable pageable) {
    return this.queryRepository.findAllByRecipientNameOrSenderNameOrderByLastMessageDateDesc(username, username, pageable);
  }

  private Optional<ConversationQueryDto> findOne(String recipientName, String senderName) {
    return this.queryRepository.findByRecipientNameAndSenderName(recipientName, senderName);
  }

}
