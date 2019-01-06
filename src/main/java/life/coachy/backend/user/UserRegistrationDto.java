/*
 * MIT License
 *
 * Copyright (c) 2018 Coachy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;
import life.coachy.backend.util.validation.Match;
import life.coachy.backend.util.validation.StringEnumeration;
import org.hibernate.validator.constraints.Length;

@Match.List({
    @Match(first = "password", second = "matchingPassword", message = "{match.password}"),
    @Match(first = "email", second = "matchingEmail", message = "{match.email")
})
public class UserRegistrationDto extends AbstractDto<User> {

  @NotEmpty(message = "{username.notEmpty}") @NotNull(message = "{username.notNull}") @Length(min = 3, max = 32, message = "{username.length}")
  private String username;

  @NotEmpty(message = "{password.notEmpty}") @NotNull(message = "{password.notNull}") @Length(min = 6, message = "{password.length}")
  private String password;
  private String matchingPassword;

  @NotEmpty(message = "{email.notEmpty}") @NotNull(message = "{email.notNull}") @Email(message = "{email.format}")
  private String email;
  private String matchingEmail;

  @NotEmpty(message = "{accountType.notEmpty}") @NotNull(message = "{accountType.notNull}") @StringEnumeration(enumClass = AccountType.class, message = "{match.accountType}")
  private String accountType;

  public UserRegistrationDto(String username, String password, String matchingPassword, String email,
      String matchingEmail, String accountType) {
    this.username = username;
    this.password = password;
    this.matchingPassword = matchingPassword;
    this.email = email;
    this.matchingEmail = matchingEmail;
    this.accountType = accountType;
  }

  public UserRegistrationDto() { // JACKSON
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

  public String getEmail() {
    return this.email;
  }

  public String getMatchingEmail() {
    return this.matchingEmail;
  }

  public AccountType getAccountType() {
    return AccountType.valueOf(this.accountType);
  }

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withUsername(this.username)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAccountType(AccountType.valueOf(this.accountType))
        .build();
  }

}
