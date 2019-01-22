package life.coachy.backend.util.validation;

import java.util.Collection;
import life.coachy.backend.error.ErrorDto;
import org.springframework.validation.FieldError;

public final class ValidationUtil {

  private ValidationUtil() {

  }

  public static ErrorDto toDto(Collection<FieldError> errors) {
    return new ErrorDto(400, errors.stream()
        .map(fieldError -> fieldError.getField() + "_" + fieldError.getDefaultMessage())
        .findFirst()
        .get());
  }

}
