package life.coachy.backend.user;

import java.util.Set;
import life.coachy.backend.schedule.ScheduleDto;
import org.bson.types.ObjectId;

public class UserDto {

  private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;
  private Set<ScheduleDto> schedules;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getUsername() {
    return this.username;
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

  public AccountType getAccountType() {
    return this.accountType;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  public Set<ScheduleDto> getSchedules() {
    return this.schedules;
  }

}
