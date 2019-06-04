package life.coachy.backend.request;

import java.util.function.Supplier;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.request.domain.RequestFacade;
import life.coachy.backend.request.domain.exception.RequestNotFoundException;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
class RequestCommand {

  private final RequestFacade requestFacade;
  private final ObjectToJsonConverter jsonConverter;

  @Autowired
  RequestCommand(RequestFacade requestFacade, ObjectToJsonConverter jsonConverter) {
    this.requestFacade = requestFacade;
    this.jsonConverter = jsonConverter;
  }

  @ShellMethod(key = "mktkn", value = "Creates a token")
  public String createToken(ObjectId requesterId) {
    return this.ifRequesterExists(requesterId, () -> {
      this.requestFacade.createToken(requesterId);
      return "Token has been successfully created";
    });
  }

  @ShellMethod(key = "tkn", value = "Displays info about the token")
  public String tokenDetails(String token) {
    return this.ifRequestExists(token, () -> this.jsonConverter.convert(this.requestFacade.fetchToken(token)));
  }

  @ShellMethod(key = "alltkn", value = "Displays all tokens belonging to specified requester id")
  public String userTokens(ObjectId requesterId) {
    return this.ifRequesterExists(requesterId, () -> this.jsonConverter.convert(this.requestFacade.fetchAllByRequesterId(requesterId)));
  }

  @ShellMethod(key = "invalidatetkn", value = "Invalidates the token")
  public String invalidateToken(String token) {
    return this.ifRequestExists(token, () -> this.requestFacade.invalidateToken(token, () -> "Token has been invalidated"));
  }

  private String ifRequesterExists(ObjectId requesterId, Supplier<String> supplier) {
    try {
      return supplier.get();
    } catch (UserNotFoundException ex) {
      return "User with id: " + requesterId + " does not exist in system!";
    }
  }

  private String ifRequestExists(String token, Supplier<String> supplier) {
    try {
      return supplier.get();
    } catch (RequestNotFoundException ex) {
      return "Request with token: " + token + " does not exist in system!";
    }
  }

}
