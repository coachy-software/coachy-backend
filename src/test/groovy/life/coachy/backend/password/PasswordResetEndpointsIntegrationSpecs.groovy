package life.coachy.backend.password

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.email.SmtpServerRule
import org.junit.Rule

class PasswordResetEndpointsIntegrationSpecs extends IntegrationSpec {

  @Rule
  public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);



}
