package life.coachy.backend.profile.recommendation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.RECOMMENDATIONS)
class Recommendation {

  @Id ObjectId id;
  private ObjectId profileUserId;
  private ObjectId from;
  private String content;
  private int rating;
  private boolean visible;
  @CreatedDate private LocalDateTime createdAt;
  @LastModifiedDate private LocalDateTime lastModifiedDate;

  Recommendation() {}

  Recommendation(RecommendationBuilder builder) {
    this.id = builder.id;
    this.profileUserId = builder.profileUserId;
    this.from = builder.from;
    this.content = builder.content;
    this.rating = builder.rating;
    this.visible = builder.visible;
    this.createdAt = builder.createdAt;
    this.lastModifiedDate = builder.lastModifiedDate;
  }

  void setContent(String content) {
    this.content = content;
  }

  void setRating(int rating) {
    this.rating = rating;
  }

  void setVisible(boolean visible) {
    this.visible = visible;
  }

}
