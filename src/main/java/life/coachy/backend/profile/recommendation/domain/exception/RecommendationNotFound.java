package life.coachy.backend.profile.recommendation.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecommendationNotFound extends RuntimeException {

}
