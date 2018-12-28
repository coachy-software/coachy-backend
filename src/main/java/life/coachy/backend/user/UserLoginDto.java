package life.coachy.backend.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLoginDto {

  @JsonProperty("username")
  @NotNull @NotEmpty
  private String username;

  @JsonProperty("password")
  @NotNull @NotEmpty
  private String password;

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

}
