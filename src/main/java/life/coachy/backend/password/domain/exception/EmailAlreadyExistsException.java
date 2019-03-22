package life.coachy.backend.password.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already exists")
public class EmailAlreadyExistsException extends RuntimeException {

}
