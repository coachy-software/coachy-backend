package life.coachy.backend.conversation.domain;

import java.util.Optional;
import java.util.function.Consumer;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.conversation.query.ConversationQueryRepository;
import life.coachy.backend.user.query.UserQueryDto;
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

  ConversationQueryDto createIfAbsent(ConversationDto dto, Conversation conversation, Consumer<ConversationQueryDto> consumer) {
    Optional<ConversationQueryDto> queryDto = this.findOne(dto.getRecipientName(), dto.getSenderName());

    if (!queryDto.isPresent()) {
      this.save(conversation);
      ConversationQueryDto newQueryDto = this.findOne(dto.getRecipientName(), dto.getSenderName()).get();
      consumer.accept(newQueryDto);
      return newQueryDto;
    }

    return queryDto.get();
  }

  Page<ConversationQueryDto> findAllByRecipientOrSender(String username, Pageable pageable) {
    return this.queryRepository.findAllByRecipientNameOrSenderNameOrderByLastMessageDateDesc(username, username, pageable);
  }

  private Optional<ConversationQueryDto> findOne(String recipientName, String senderName) {
    return this.queryRepository.findByRecipientNameAndSenderName(recipientName, senderName);
  }

}
