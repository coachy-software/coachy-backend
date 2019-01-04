package life.coachy.backend.user;

import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserService implements CrudOperationsService<User, ObjectId> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<User> findByName(String name) {
    return this.userRepository.findByUsername(name);
  }

  @Override
  public Optional<User> findById(ObjectId objectId) {
    return this.userRepository.findById(objectId);
  }

  @Override
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public <S extends User> S save(S entity) {
    entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
    return this.userRepository.save(entity);
  }

  @Override
  public void deleteById(ObjectId objectId) {
    this.userRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.userRepository.existsById(objectId);
  }

}
