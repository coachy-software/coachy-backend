package life.coachy.backend.old_user.dto;

import life.coachy.backend.old_user.UserMapper;
import life.coachy.backend.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapperFactory extends AbstractDtoMapperFactory<UserMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.old_user.dto");

  public UserDtoMapperFactory() {
    super(UserMapper.INSTANCE, REFLECTIONS);
  }

}
