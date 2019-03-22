package life.coachy.backend.password.query;

import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.springframework.data.repository.Repository;

public interface PasswordResetQueryRepository extends QueryFetchOneRepository<PasswordResetQueryDto, String>, Repository<PasswordResetQueryDto, String> {

  Optional<PasswordResetQueryDto> findByToken(String token);

}
