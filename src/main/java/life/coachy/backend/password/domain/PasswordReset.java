package life.coachy.backend.password.domain;

import java.util.Date;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.TOKENS)
class PasswordReset {

  @Id private String email;
  private String token;
  @Version private long version;
  @Indexed(expireAfterSeconds = 10800) @CreatedDate private Date createdAt;

  PasswordReset(PasswordResetBuilder builder) {
    this.email = builder.email;
    this.token = builder.token;
  }

  PasswordReset() {}

  public static PasswordResetBuilder builder() {
    return PasswordResetBuilder.create();
  }

}
