package life.coachy.backend.profile.recommendation

import groovy.transform.CompileStatic
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDtoBuilder
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDtoBuilder
import org.bson.types.ObjectId

@CompileStatic
trait SampleRecommendations {

  RecommendationUpdateCommandDto recommendationUpdateSample = RecommendationUpdateCommandDtoBuilder.create()
      .withContent("updated test content")
      .withRating(10)
      .build();

  RecommendationCreateCommandDto recommendationCreateSample = RecommendationCreateCommandDtoBuilder.create()
      .withProfileUserId(ObjectId.get())
      .withFrom(ObjectId.get())
      .withContent("test content")
      .withRating(10)
      .build();

  RecommendationCreateCommandDto invalidRecommendationCreateSample = RecommendationCreateCommandDtoBuilder.create()
      .withProfileUserId(ObjectId.get())
      .withFrom(ObjectId.get())
      .build();

}
