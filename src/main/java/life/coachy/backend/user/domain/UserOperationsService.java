package life.coachy.backend.user.domain;

import java.util.function.Supplier;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import life.coachy.backend.user.domain.exception.UserAlreadyExistsException;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserOperationsService {

  private final UserQueryDtoRepository userQueryDtoRepository;

  @Autowired
  public UserOperationsService(UserQueryDtoRepository userQueryDtoRepository) {
    this.userQueryDtoRepository = userQueryDtoRepository;
  }

  public void registerByDto(UserRegisterCommandDto dto, Supplier<User> userSupplier) {
    if (this.userQueryDtoRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail())) {
      throw new UserAlreadyExistsException();
    }

    userSupplier.get();
  }

}
