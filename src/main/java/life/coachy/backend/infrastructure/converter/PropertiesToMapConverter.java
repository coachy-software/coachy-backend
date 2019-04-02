package life.coachy.backend.infrastructure.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import org.springframework.stereotype.Component;

@Component
public class PropertiesToMapConverter implements Converter<Map<String, Object>, CommandDtoMarker> {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public Map<String, Object> convert(CommandDtoMarker dto) {
    return OBJECT_MAPPER.convertValue(dto, new TypeReference<Map<String, Object>>() {});
  }

}
