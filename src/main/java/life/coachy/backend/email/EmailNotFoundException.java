package life.coachy.backend.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email not found")
public class EmailNotFoundException extends RuntimeException {

}
