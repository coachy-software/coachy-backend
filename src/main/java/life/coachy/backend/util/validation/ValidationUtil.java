package life.coachy.backend.util.validation;

import java.util.Collection;
import java.util.function.Supplier;
import life.coachy.backend.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

public final class ValidationUtil {

  private ValidationUtil() {

  }

  public static ErrorDto toDto(Collection<FieldError> errors) {
    return new ErrorDto(400, errors.stream()
        .map(fieldError -> fieldError.getField() + "_" + fieldError.getDefaultMessage())
        .findFirst()
        .orElse("n/a"));
  }

  public static <T> ResponseEntity<?> validate(T dto, SmartValidator smartValidator, BindingResult result,
      Supplier<ResponseEntity<?>> supplier) {
    smartValidator.validate(dto, result);

    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationUtil.toDto(result.getFieldErrors()));
    }

    return supplier.get();
  }

}
