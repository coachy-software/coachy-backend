package life.coachy.backend.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDto<T extends IdentifiableEntity<?>> {

  @JsonIgnore
  public String getEntityName() {
    throw new UnsupportedOperationException();
  }

  public String toJson() {
    try {
      return new ObjectMapper().writer().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }

}
