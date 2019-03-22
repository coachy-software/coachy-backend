package life.coachy.backend.password.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PasswordResetRepository extends CommandRepository<PasswordReset, String> {

}
