package life.coachy.backend.user;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.user.dto.UserDtoMapperFactory;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.CrudOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserCrudService implements CrudOperationsService<User, ObjectId> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDtoMapperFactory mapperFactory;

  @Autowired
  public UserCrudService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      UserDtoMapperFactory mapperFactory) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.mapperFactory = mapperFactory;
  }

  @Override
  public Optional<User> findByName(String name) {
    Preconditions.checkNotNull(name, "User entity name cannot be null");
    return this.userRepository.findByUsername(name);
  }

  @Override
  public Optional<User> findById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "User entity identifier cannot be null");
    return this.userRepository.findById(objectId);
  }

  @Override
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public User save(User entity) {
    Preconditions.checkNotNull(entity, "User entity cannot be null");
    return this.saveEntity(entity);
  }

  @Override
  public <S extends AbstractDto> User save(S dto) {
    Preconditions.checkNotNull(dto, "User DTO cannot be null");
    return this.saveEntity(this.mapperFactory.obtainEntity(dto));
  }

  @Override
  public void deleteById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "User entity identifier cannot be null");
    this.userRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "User entity identifier cannot be null");
    return this.userRepository.existsById(objectId);
  }

  @Override
  public List<User> findAll(Predicate predicate) {
    Preconditions.checkNotNull(predicate);
    return Lists.newArrayList(this.userRepository.findAll(predicate));
  }

  @Override
  public Page<User> findAll(Predicate predicate, Pageable pageable) {
    Preconditions.checkNotNull(predicate);
    Preconditions.checkNotNull(pageable);

    return this.userRepository.findAll(predicate, pageable);
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    Preconditions.checkNotNull(pageable);
    return this.userRepository.findAll(pageable);
  }

  boolean existsByEmail(String email) {
    Preconditions.checkNotNull(email, "User entity email cannot be null");
    return this.userRepository.existsByEmail(email);
  }

  Optional<User> findByEmail(String email) {
    Preconditions.checkNotNull(email, "User entity name cannot be null");
    return this.userRepository.findByEmail(email);
  }

  User savePassword(User user, String password) {
    Preconditions.checkNotNull(user, "User entity cannot be null");
    Preconditions.checkNotNull(password, "User entity password cannot be null");

    user.setPassword(this.passwordEncoder.encode(password));
    return this.userRepository.save(user);
  }

  private User saveEntity(User entity) {
    Preconditions.checkNotNull(entity, "User entity cannot be null");

    this.findByName(entity.getUsername())
        .ifPresent(user -> {
          if (!entity.getPassword().equals(user.getPassword())) {
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
          }
        });

    return this.userRepository.save(entity);
  }

}
