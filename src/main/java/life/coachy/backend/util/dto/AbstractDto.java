package life.coachy.backend.util.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.coachy.backend.util.IdentifiableEntity;

public abstract class AbstractDto<T extends IdentifiableEntity<?>> {

  protected T toEntity() {
    throw new UnsupportedOperationException();
  }

  public String toJson() {
    try {
      return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }

}
