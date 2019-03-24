package life.coachy.backend.exercise.template.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Exercise template not found")
public class ExerciseTemplateNotFoundException extends RuntimeException {

}
