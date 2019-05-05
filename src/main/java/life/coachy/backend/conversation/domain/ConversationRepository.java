package life.coachy.backend.conversation.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface ConversationRepository extends CommandRepository<Conversation, ObjectId> {

}
