package life.coachy.backend.user.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface UserRepository extends CommandRepository<User, ObjectId>, UserRepositoryExtension {

}
