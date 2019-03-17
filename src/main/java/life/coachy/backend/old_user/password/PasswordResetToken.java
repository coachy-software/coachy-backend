package life.coachy.backend.old_user.password;

import java.util.Date;
import life.coachy.backend.util.IdentifiableEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("password-tokens")
class PasswordResetToken implements IdentifiableEntity<String> {

  @Id
  private String email;
  private String token;
  @Version
  private long version;
  @Indexed(expireAfterSeconds = 10800) // 3 hours
  @CreatedDate
  private Date createdAt;

  PasswordResetToken(String email, String token) {
    this.email = email;
    this.token = token;
  }

  @Override
  public String getIdentifier() {
    return this.email;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getVersion() {
    return this.version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
