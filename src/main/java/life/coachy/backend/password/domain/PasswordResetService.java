package life.coachy.backend.password.domain;

import java.util.Optional;
import java.util.function.Consumer;
import life.coachy.backend.password.domain.exception.EmailAlreadyExistsException;
import life.coachy.backend.password.domain.exception.PasswordResetTokenNotFoundException;
import life.coachy.backend.password.query.PasswordResetQueryDto;
import life.coachy.backend.password.query.PasswordResetQueryRepository;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import life.coachy.backend.user.query.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PasswordResetService {

  private final PasswordResetQueryRepository queryRepository;
  private final PasswordResetRepository repository;

  @Autowired
  public PasswordResetService(PasswordResetQueryRepository queryRepository, PasswordResetRepository repository) {
    this.queryRepository = queryRepository;
    this.repository = repository;
  }

  void create(String email, UserQueryRepository userQueryRepository, String token) {
    if (!userQueryRepository.existsByEmail(email)) {
      throw new UserNotFoundException();
    }

    this.findById(email).ifPresent(passwordResetQueryDto -> {
      throw new EmailAlreadyExistsException();
    });

    PasswordReset passwordReset = PasswordReset.builder()
        .withEmail(email)
        .withToken(token)
        .build();
    this.save(passwordReset);
  }

  void reset(String token, Consumer<String> emailConsumer) {
    PasswordResetQueryDto passwordReset = this.findByToken(token).orElseThrow(PasswordResetTokenNotFoundException::new);
    String email = passwordReset.getEmail();

    emailConsumer.accept(email);
    this.repository.deleteById(email);
  }

  private Optional<PasswordResetQueryDto> findById(String email) {
    return this.queryRepository.findById(email);
  }

  private Optional<PasswordResetQueryDto> findByToken(String token) {
    return this.queryRepository.findByToken(token);
  }

  private void save(PasswordReset passwordReset) {
    this.repository.save(passwordReset);
  }

}
