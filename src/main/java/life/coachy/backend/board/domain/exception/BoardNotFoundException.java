package life.coachy.backend.board.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Board not found")
public class BoardNotFoundException extends RuntimeException {

}
