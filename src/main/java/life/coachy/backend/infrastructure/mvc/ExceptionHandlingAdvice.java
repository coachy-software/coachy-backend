package life.coachy.backend.infrastructure.mvc;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionHandlingAdvice {

  @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
  ResponseEntity<?> handleNonUniqueResults() {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

}
