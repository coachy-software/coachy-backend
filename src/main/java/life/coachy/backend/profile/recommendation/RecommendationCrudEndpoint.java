package life.coachy.backend.profile.recommendation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.profile.recommendation.domain.RecommendationFacade;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  ResponseEntity<?> create(@Valid @RequestBody RecommendationCreateCommandDto dto) {
    return ResponseEntity.created(this.recommendationFacade.create(dto)).build();
  }

  @ApiOperation("Displays all recommendations belonging to specified profile")
  @RequiresAuthenticated
  @GetMapping(ApiLayers.PROFILES + "/{id}/" + ApiLayers.RECOMMENDATIONS)
  ResponseEntity<Set<Map<String, Object>>> fetchAll(@PathVariable @ApiParam("Profile user's id") ObjectId id) {
    return ResponseEntity.ok(this.recommendationFacade.fetchAll(id));
  }

  @ApiOperation("Displays recommendation by its id")
  @RequiresAuthenticated
  @GetMapping(ApiLayers.PROFILES + "/{id}/" + ApiLayers.RECOMMENDATIONS + "/{recommendationId}")
  ResponseEntity<Map<String, Object>> fetchOne(@PathVariable ObjectId id, @PathVariable @ApiParam("Recommendation id") ObjectId recommendationId) {
    return ResponseEntity.ok(this.recommendationFacade.fetchOne(recommendationId));
  }

}
