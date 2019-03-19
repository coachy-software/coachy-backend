package life.coachy.backend.user.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface UserRepository extends MongoRepository<User, ObjectId> {

}
