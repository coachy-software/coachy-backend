package life.coachy.backend.profile.recommendation;

import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermission;
import life.coachy.backend.profile.recommendation.domain.RecommendationFacade;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.PROFILES)
class RecommendationOperationsEndpoint {

  private final RecommendationFacade recommendationFacade;

  @Autowired
  RecommendationOperationsEndpoint(RecommendationFacade recommendationFacade) {
    this.recommendationFacade = recommendationFacade;
  }

  @ApiOperation("Requests for recommendation revision")
  @RequiresPermission("recommendation.{recommendationId}.owner")
  @RequiresAuthenticated
  @PostMapping("{id}/" + ApiLayers.RECOMMENDATIONS + "/{recommendationId}/request-revision")
  ResponseEntity<?> revisionRequest(@PathVariable ObjectId id, @PathVariable ObjectId recommendationId) {
    this.recommendationFacade.requestRevision(recommendationId);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Commits changes made in revision")
  @RequiresPermission("recommendation.{recommendationId}.update")
  @RequiresAuthenticated
  @PostMapping("{id}/" + ApiLayers.RECOMMENDATIONS + "/{recommendationId}/commit-revision")
  ResponseEntity<?> commitRevision(@PathVariable ObjectId id, @PathVariable ObjectId recommendationId, @RequestBody @Valid RecommendationUpdateCommandDto dto) {
    this.recommendationFacade.commitRevision(recommendationId, dto);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Changes recommendation visibility")
  @RequiresPermission("recommendation.{recommendationId}.owner")
  @RequiresAuthenticated
  @PostMapping("{id}/" + ApiLayers.RECOMMENDATIONS + "/{recommendationId}/visibility")
  ResponseEntity<?> changeVisibilityStatus(@PathVariable ObjectId id, @PathVariable ObjectId recommendationId, @RequestBody Map<String, String> payload) {
    if (payload.get("visible") == null) {
      return ResponseEntity.badRequest().build();
    }

    this.recommendationFacade.changeVisibilityStatus(recommendationId, Boolean.valueOf(payload.get("visible")));
    return ResponseEntity.noContent().build();
  }

}
