package life.coachy.backend.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import javax.mail.internet.MimeMessage;
import org.junit.rules.ExternalResource;

public class SmtpServerRule extends ExternalResource {

  private GreenMail smtpServer;
  private int port;

  public SmtpServerRule(int port) {
    this.port = port;
  }

  @Override
  protected void before() throws Throwable {
    super.before();
    this.smtpServer = new GreenMail(new ServerSetup(this.port, null, "smtp"));
    this.smtpServer.start();
  }

  public MimeMessage[] getMessages() {
    return this.smtpServer.getReceivedMessages();
  }

  @Override
  protected void after() {
    super.after();
    this.smtpServer.stop();
  }

  protected GreenMail getSmtpServer() {
    return this.smtpServer;
  }

}