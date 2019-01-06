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

package life.coachy.backend.error;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  private final static String ERROR_PATH = "/error";
  private final ErrorAttributes errorAttributes;
  private final MessageSource messageSource;

  @Autowired
  ErrorController(ErrorAttributes errorAttributes, MessageSource messageSource) {
    this.errorAttributes = errorAttributes;
    this.messageSource = messageSource;
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping(value = ERROR_PATH) // For all HTTP methods
  public ErrorDto error(WebRequest webRequest, HttpServletResponse response) {
    Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, false);

    if (response.getStatus() == 400 && errorAttributes.get("errors") != null) {
      Collection<FieldError> errors = (Collection<FieldError>) errorAttributes.get("errors");

      return new ErrorDto(400, errors.stream()
          .map(fieldError -> this.messageSource.getMessage(fieldError, Locale.getDefault()))
          .findFirst().get());
    }

    return new ErrorDto(response.getStatus(), errorAttributes.get("error").toString());
  }

}
