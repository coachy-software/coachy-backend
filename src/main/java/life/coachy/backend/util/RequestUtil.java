package life.coachy.backend.util;

import life.coachy.backend.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public final class RequestUtil {

  private RequestUtil() {

  }

  public static ResponseEntity<?> errorResponse(BindingResult result) {
    return ResponseEntity.badRequest().body(new ErrorDto(400, result.getAllErrors().get(0).getDefaultMessage()));
  }

}
