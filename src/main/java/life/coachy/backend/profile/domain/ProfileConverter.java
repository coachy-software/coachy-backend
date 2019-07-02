package life.coachy.backend.profile.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import life.coachy.backend.infrastructure.converter.Converter;
import life.coachy.backend.profile.query.ProfileQueryDto;
import org.springframework.stereotype.Component;

class ProfileConverter implements Converter<ProfileQueryDto, Map<String, Object>> {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public ProfileQueryDto convert(Map<String, Object> value) {
    return OBJECT_MAPPER.convertValue(value, ProfileQueryDto.class);
  }

}
