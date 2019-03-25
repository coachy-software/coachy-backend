package life.coachy.backend.user.domain;

import life.coachy.backend.infrastructure.converter.PropertiesToMapConverter;
import life.coachy.backend.user.domain.dto.UserUpdateEntireEntityCommandDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserCrudService {

  private final UserRepository userRepository;
  private final PropertiesToMapConverter propertiesToMapConverter;

  @Autowired
  public UserCrudService(UserRepository userRepository, PropertiesToMapConverter propertiesToMapConverter) {
    this.userRepository = userRepository;
    this.propertiesToMapConverter = propertiesToMapConverter;
  }

  void save(User user) {
    this.userRepository.save(user);
  }

  void delete(ObjectId id) {
    this.userRepository.deleteById(id);
  }

  void convertPropertiesToMapAndSave(ObjectId id, UserUpdateEntireEntityCommandDto dto) {
    this.userRepository.updateEntireEntity(id, this.propertiesToMapConverter.convert(dto));
  }

}
