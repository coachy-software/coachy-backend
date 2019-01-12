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

package life.coachy.backend.user.password;

import java.util.Optional;
import javax.mail.MessagingException;
import javax.validation.Valid;
import life.coachy.backend.email.EmailFacade;
import life.coachy.backend.user.UserFacade;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PasswordResetController {

  private final UserFacade userFacade;
  private final EmailFacade emailService;
  private final PasswordResetTokenRepository repository;

  @Value("${frontend.uri}")
  private String resetLink;

  @Autowired
  public PasswordResetController(UserFacade userFacade, EmailFacade emailFacade,
      PasswordResetTokenRepository repository) {
    this.userFacade = userFacade;
    this.emailService = emailFacade;
    this.repository = repository;
  }

  @PostMapping("/api/create-token/{email}")
  public ResponseEntity<PasswordResetTokenDto> createToken(@PathVariable String email) throws MessagingException {
    if (!this.userFacade.exists(email)) {
      return ResponseEntity.notFound().build();
    }

    if (this.repository.findById(email).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    PasswordResetToken token = new PasswordResetToken(email, RandomString.make(32));
    this.repository.save(token);

    this.emailService.sendResetPasswordEmail(email, this.resetLink + "reset-password?token=" + token.getToken());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/api/reset-password/{token}")
  public ResponseEntity<PasswordResetTokenDto> resetPassword(@PathVariable String token,
      @RequestBody @Valid PasswordResetTokenDto dto) {
    Optional<PasswordResetToken> passwordResetToken = this.repository.findByToken(token);

    if (!passwordResetToken.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    String email = passwordResetToken.get().getEmail();
    this.userFacade.resetPassword(email, dto.getNewPassword());
    this.repository.deleteById(email);

    return ResponseEntity.noContent().build();
  }

}
