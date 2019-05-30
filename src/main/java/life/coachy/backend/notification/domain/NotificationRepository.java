package life.coachy.backend.notification.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface NotificationRepository extends CommandRepository<Notification, ObjectId> {

}
