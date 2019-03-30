package life.coachy.backend.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectToJsonConverter {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public String convert(Object value) {
    try {
      return OBJECT_MAPPER.writer().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }

}
