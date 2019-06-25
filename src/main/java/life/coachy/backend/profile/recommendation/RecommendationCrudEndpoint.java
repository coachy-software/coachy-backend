package life.coachy.backend.profile.recommendation;

import io.swagger.annotations.ApiOperation;
import java.util.Set;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.profile.recommendation.domain.RecommendationFacade;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RecommendationCrudEndpoint {

  private final RecommendationFacade recommendationFacade;

  @Autowired
  RecommendationCrudEndpoint(RecommendationFacade recommendationFacade) {
    this.recommendationFacade = recommendationFacade;
  }

  @ApiOperation("Creates a recommendation on specified profile")
  @RequiresAuthenticated
  @PostMapping(ApiLayers.API_ROOT + ApiLayers.RECOMMENDATIONS)
  ResponseEntity<?> create(@RequestBody RecommendationCreateCommandDto dto) {
    this.recommendationFacade.create(dto);
    return ResponseEntity.ok().build();
  }

  @ApiOperation("Displays all recommendations belonging to specified profile")
  @RequiresAuthenticated
  @GetMapping(ApiLayers.PROFILES + "{id}" + ApiLayers.RECOMMENDATIONS)
  ResponseEntity<Set<RecommendationQueryDto>> fetchAll(@PathVariable ObjectId id) {
    return ResponseEntity.ok(this.recommendationFacade.fetchAll(id));
  }

}
