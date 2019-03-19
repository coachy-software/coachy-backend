package life.coachy.backend.user.domain.dto;

public class UserRegisterCommandDto {

  private String email;
  private String username;
  private String password;
  private String matchingPassword;
  private AccountTypeDto accountType;

  public String getEmail() {
    return this.email;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getMatchingPassword() {
    return this.matchingPassword;
  }

  public AccountTypeDto getAccountType() {
    return this.accountType;
  }

}
