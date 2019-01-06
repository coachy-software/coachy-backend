package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class UserCrudDto extends AbstractDto<User> {

  @NotEmpty @NotNull @Length(min = 3, max = 32, message = "Username must be at least 3 and up to 32 characters long.")
  private String username;

  @NotEmpty @NotNull @Length(min = 6, message = "Password must be at least 6 characters long.")
  private String password;

  @NotEmpty @NotNull @Email(message = "Must be a well-formed email address")
  private String email;

  @NotEmpty @NotNull @URL
  private String avatar;

  public UserCrudDto(String username, String password, String email, String avatar) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
  }

  public UserCrudDto() {
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getEmail() {
    return this.email;
  }

  public String getAvatar() {
    return this.avatar;
  }

  @Override
  public String getName() {
    return this.username;
  }

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withUsername(this.username)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAvatar(this.avatar)
        .build();
  }

}
