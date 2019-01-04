package life.coachy.backend.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;

public class UserAuthenticationDto extends AbstractDto<User> {

  @NotNull @NotEmpty
  private String username;

  @NotNull @NotEmpty
  private String password;

  public UserAuthenticationDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public UserAuthenticationDto() { // JACKSON
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withUsername(this.username)
        .withPassword(this.password)
        .build();
  }

}
