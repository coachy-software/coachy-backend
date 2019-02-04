package life.coachy.backend.user;

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
    return this.userRepository.findByUsername(name);
  }

  @Override
  public Optional<User> findById(ObjectId objectId) {
    return this.userRepository.findById(objectId);
  }

  Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public User save(User entity) {
    return this.saveEntity(entity);
  }

  @Override
  public <S extends AbstractDto> User save(S dto) {
    return this.saveEntity(this.mapperFactory.obtainEntity(dto));
  }

  @Override
  public void deleteById(ObjectId objectId) {
    this.userRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.userRepository.existsById(objectId);
  }

  @Override
  public List<User> findAll(Predicate predicate) {
    return Lists.newArrayList(this.userRepository.findAll(predicate));
  }

  @Override
  public Page<User> findAll(Predicate predicate, Pageable pageable) {
    return this.userRepository.findAll(predicate, pageable);
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return this.userRepository.findAll(pageable);
  }

  boolean existsByEmail(String email) {
    return this.userRepository.existsByEmail(email);
  }

  User savePassword(User user, String password) {
    user.setPassword(this.passwordEncoder.encode(password));
    return this.userRepository.save(user);
  }

  private User saveEntity(User entity) {
    Optional<User> databaseUser = this.findByName(entity.getUsername());
    if (databaseUser.isPresent() && !entity.getPassword().equals(databaseUser.get().getPassword())) {
      entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
    }

    return this.userRepository.save(entity);
  }

}
