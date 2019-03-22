package life.coachy.backend.password.query;

import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.TOKENS)
public class PasswordResetQueryDto implements QueryDtoMarker {

  @Id private String email;
  private String token;

  public String getEmail() {
    return this.email;
  }

  public String getToken() {
    return this.token;
  }

}
