package life.coachy.backend.error;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
class ErrorEndpoints implements org.springframework.boot.web.servlet.error.ErrorController {

  private final ErrorAttributes errorAttributes;

  @Autowired
  ErrorEndpoints(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getErrorPath() {
    return ApiLayers.ERROR;
  }

  @RequestMapping(ApiLayers.ERROR) // For all HTTP methods
  public ErrorDto error(WebRequest webRequest, HttpServletResponse response) {
    Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, false);
    return new ErrorDto(response.getStatus(), errorAttributes.get("error").toString());
  }

}
