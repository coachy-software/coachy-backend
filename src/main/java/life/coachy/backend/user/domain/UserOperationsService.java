package life.coachy.backend.user.domain;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import life.coachy.backend.user.domain.dto.UserChangePasswordCommandDto;
import life.coachy.backend.user.domain.exception.IncorrectCredentialsException;
import life.coachy.backend.user.domain.exception.UserAlreadyExistsException;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserOperationsService {

  private final UserQueryRepository queryDtoRepository;
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserOperationsService(UserQueryRepository queryDtoRepository, UserRepository repository, PasswordEncoder passwordEncoder) {
    this.queryDtoRepository = queryDtoRepository;
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  void checkIfUsernameOrEmailExists(String username, String email, Runnable runnable) {
    if (this.queryDtoRepository.existsByUsernameOrEmail(username, email)) {
      throw new UserAlreadyExistsException();
    }

    runnable.run();
  }

  void checkIfUsernameOrEmailCanBeUpdated(ObjectId id, String username, String email, Consumer<UserQueryDto> queryDtoConsumer) {
    UserQueryDto queryDto = this.queryDtoRepository.findById(id).orElseThrow(UserNotFoundException::new);

    boolean canUsernameBeUpdated = !this.queryDtoRepository.existsByUsername(username) || username.equals(queryDto.getUsername());
    boolean canEmailBeUpdated = !this.queryDtoRepository.existsByEmail(email) || email.equals(queryDto.getEmail());

    if (canUsernameBeUpdated && canEmailBeUpdated) {
      queryDtoConsumer.accept(queryDto);
      return;
    }

    throw new UserAlreadyExistsException();
  }

  void ifExists(ObjectId id, Runnable runnable) {
    if (!this.queryDtoRepository.existsByIdentifier(id)) {
      throw new UserNotFoundException();
    }

    runnable.run();
  }

  void checkIfExists(ObjectId id) {
    if (!this.queryDtoRepository.existsByIdentifier(id)) {
      throw new UserNotFoundException();
    }
  }

  void validateAndChangePassword(UserQueryDto userQueryDto, UserChangePasswordCommandDto dto) {
    if (!this.passwordEncoder.matches(dto.getOldPassword(), userQueryDto.getPassword())) {
      throw new IncorrectCredentialsException();
    }

    this.resetPassword(userQueryDto.getEmail(), dto.getNewPassword());
  }

  void addPermissions(ObjectId id, String... permissions) {
    this.modifyPermissions(id, user -> Stream.of(user.getPermissions(), Sets.newHashSet(permissions))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet()));
  }

  void removePermissions(ObjectId id, ObjectId permissionId) {
    this.modifyPermissions(id, userQueryDto -> userQueryDto.getPermissions().stream()
        .filter(permission -> !permission.contains(permissionId.toHexString()))
        .collect(Collectors.toSet()));
  }

  void resetPassword(String email, String newPassword) {
    if (!this.queryDtoRepository.existsByEmail(email)) {
      throw new UserNotFoundException();
    }

    this.repository.updatePasswordByEmail(email, newPassword);
  }

  void updateBoardId(ObjectId boardId, ObjectId userId) {
    this.repository.updateBoardIdById(boardId, userId);
  }

  private void modifyPermissions(ObjectId id, Function<UserQueryDto, Set<String>> function) {
    this.queryDtoRepository.findById(id).ifPresent(userQueryDto -> this.repository.updatePermissionsById(id, function.apply(userQueryDto)));
  }

}
