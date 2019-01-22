package life.coachy.backend.error;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  private final static String ERROR_PATH = "/error";
  private final ErrorAttributes errorAttributes;

  @Autowired
  ErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping(value = ERROR_PATH) // For all HTTP methods
  public ErrorDto error(WebRequest webRequest, HttpServletResponse response) {
    Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, false);
    return new ErrorDto(response.getStatus(), errorAttributes.get("error").toString());
  }

}
