package life.coachy.backend.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
class EmailCreationService {

  private final TemplateEngine templateEngine;

  @Autowired
  public EmailCreationService(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  String createResetPasswordTemplateAsString(String link) {
    Context context = new Context();
    context.setVariable("link", link);

    return this.templateEngine.process("reset-password-email.html", context);
  }

}
