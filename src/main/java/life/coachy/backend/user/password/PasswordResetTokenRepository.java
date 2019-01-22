package life.coachy.backend.user.password;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

  Optional<PasswordResetToken> findByToken(String token);

}
