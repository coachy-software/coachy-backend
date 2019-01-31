package life.coachy.backend.user;

import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserService implements CrudOperationsService<User, String> {

  private final UserMongoRepository userMongoRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserMongoRepository userMongoRepository, PasswordEncoder passwordEncoder) {
    this.userMongoRepository = userMongoRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<User> findByName(String name) {
    return this.userMongoRepository.findByUsername(name);
  }

  @Override
  public Optional<User> findById(String id) {
    return this.userMongoRepository.findById(id);
  }

  Optional<User> findByEmail(String email) {
    return this.userMongoRepository.findByEmail(email);
  }

  @Override
  public List<User> findAll() {
    return this.userMongoRepository.findAll();
  }

  @Override
  public <S extends User> S save(S entity) {
    Optional<User> databaseUser = this.findByName(entity.getUsername());
    if (databaseUser.isPresent() && !entity.getPassword().equals(databaseUser.get().getPassword())) {
      entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
    }

    return this.userMongoRepository.save(entity);
  }

  @Override
  public void deleteById(String id) {
    this.userMongoRepository.deleteById(id);
  }

  @Override
  public boolean existsById(String id) {
    return this.userMongoRepository.existsById(id);
  }

  boolean existsByEmail(String email) {
    return this.userMongoRepository.existsByEmail(email);
  }

  User savePassword(User user, String password) {
    user.setPassword(this.passwordEncoder.encode(password));
    return this.userMongoRepository.save(user);
  }

}
