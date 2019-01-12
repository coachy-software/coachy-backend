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