package life.coachy.backend.user.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import life.coachy.backend.user.domain.dto.UserUpdateEntireEntityCommandDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserCrudService {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final UserRepository userRepository;

  @Autowired
  public UserCrudService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  void save(User user) {
    this.userRepository.save(user);
  }

  void delete(ObjectId id) {
    this.userRepository.deleteById(id);
  }

  void convertAndSave(ObjectId id, UserUpdateEntireEntityCommandDto dto) {
    this.userRepository.updateEntireEntity(id, OBJECT_MAPPER.convertValue(dto, new TypeReference<Map<String, Object>>() {}));
  }

}
