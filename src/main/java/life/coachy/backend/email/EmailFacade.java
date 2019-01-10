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

package life.coachy.backend.email;

import java.util.Locale;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailFacade {

  private final EmailCreationService emailCreationService;
  private final EmailService emailService;
  private final MessageSource messageSource;

  @Autowired
  public EmailFacade(EmailCreationService emailCreationService, EmailService emailService,
      MessageSource messageSource) {
    this.emailCreationService = emailCreationService;
    this.emailService = emailService;
    this.messageSource = messageSource;
  }

  public void sendResetPasswordEmail(String to, String resetLink) {
    try {
      String content = this.emailCreationService.createResetPasswordTemplateAsString(resetLink);
      String topic = this.messageSource.getMessage("email.passwordReset.topic", null, Locale.getDefault());
      this.emailService.sendMessage(to, topic, content);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
