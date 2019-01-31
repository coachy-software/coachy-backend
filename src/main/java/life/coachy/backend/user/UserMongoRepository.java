package life.coachy.backend.user;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

interface UserMongoRepository extends MongoRepository<User, String> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
