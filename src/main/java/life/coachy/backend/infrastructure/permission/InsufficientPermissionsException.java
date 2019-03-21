package life.coachy.backend.infrastructure.permission;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No permissions")
class InsufficientPermissionsException extends RuntimeException {

}
