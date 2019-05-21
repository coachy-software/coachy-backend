package life.coachy.backend.upload;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
class UploadExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<Object> handleFileSizeLimitException() {
    return new ResponseEntity<>(Collections.singletonMap("message", "Maximum upload size exceeded"), HttpStatus.BAD_REQUEST);
  }

}
