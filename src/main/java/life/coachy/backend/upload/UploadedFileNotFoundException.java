package life.coachy.backend.upload;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class UploadedFileNotFoundException extends RuntimeException {

}
