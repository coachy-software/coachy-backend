package life.coachy.backend.user;

import life.coachy.backend.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
class UserDtoMapperFactory extends AbstractDtoMapperFactory<UserMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.user");

  public UserDtoMapperFactory() {
    super(UserMapper.INSTANCE, REFLECTIONS);
  }

}
