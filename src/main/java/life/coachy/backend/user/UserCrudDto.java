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
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.util.AbstractDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class UserCrudDto extends AbstractDto<User> {

  @NotEmpty(message = "{displayName.notEmpty}") @NotNull(message = "{displayName.notNull}") @Length(min = 3, max = 32, message = "{displayName.length}")
  private String displayName;

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE, message = "{password.pattern}")
  @NotEmpty(message = "{password.notEmpty}") @NotNull(message = "{password.notNull}") @Length(min = 6, message = "{password.length}")
  private String password;

  @NotEmpty(message = "{email.notEmpty}") @NotNull(message = "{email.notNull}") @Email(message = "{email.format}")
  private String email;

  @NotEmpty(message = "{avatar.notEmpty}") @NotNull(message = "{avatar.notNull}") @URL(message = "{avatar.format}")
  private String avatar;

  public UserCrudDto(String displayName, String password, String email, String avatar) {
    this.displayName = displayName;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
  }

  public UserCrudDto() {
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

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withDisplayName(this.displayName)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAvatar(this.avatar)
        .build();
  }

}
