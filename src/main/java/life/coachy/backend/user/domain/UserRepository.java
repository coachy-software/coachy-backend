package life.coachy.backend.user.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface UserRepository extends CommandRepository<User, ObjectId>, UserUpdateRepositoryExtension {

}
