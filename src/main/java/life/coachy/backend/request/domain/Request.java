package life.coachy.backend.request.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.REQUESTS)
class Request {

  @Id private ObjectId identifier;
  private String token;
  @Version private long version;
  @Indexed(expireAfterSeconds = 10800) @CreatedDate private LocalDateTime createdAt;

  Request() {}

  Request(RequestBuilder builder) {
    this.identifier = builder.identifier;
    this.token = builder.token;
    this.version = builder.version;
    this.createdAt = builder.createdAt;
  }

}
