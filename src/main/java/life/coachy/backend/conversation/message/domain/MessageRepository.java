package life.coachy.backend.conversation.message.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface MessageRepository extends CommandRepository<Message, ObjectId> {

}
