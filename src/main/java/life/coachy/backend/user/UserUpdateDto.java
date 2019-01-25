package life.coachy.backend.user;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.schedule.ScheduleDto;
import life.coachy.backend.util.AbstractDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class UserUpdateDto extends AbstractDto<User> {

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 3, max = 32, message = "{length}")
  private String displayName;

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE, message = "{pattern}")
  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 6, message = "{length}")
  private String password;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Email(message = "{pattern}")
  private String email;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @URL(message = "{pattern}")
  private String avatar;

  private Set<ScheduleDto> schedules;

  UserUpdateDto(String displayName, String password, String email, String avatar) {
    this.displayName = displayName;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
  }

  UserUpdateDto() {
  }

  public String getDisplayName() {
    return this.displayName;
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

  public Set<ScheduleDto> getSchedules() {
    return this.schedules;
  }

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withDisplayName(this.displayName)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAvatar(this.avatar)
        .withSchedules(this.schedules)
        .build();
  }

}
