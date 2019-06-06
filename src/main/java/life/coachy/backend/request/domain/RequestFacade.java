package life.coachy.backend.request.domain;

import java.util.Set;
import java.util.function.Supplier;
import life.coachy.backend.request.domain.exception.RequestNotFoundException;
import life.coachy.backend.request.query.RequestQueryDto;
import life.coachy.backend.request.query.RequestQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;

public class RequestFacade {

  private final RequestQueryRepository queryRepository;
  private final RequestTokenGenerator tokenGenerator;
  private final RequestService requestService;
  private final UserFacade userFacade;

  RequestFacade(RequestQueryRepository queryRepository, RequestTokenGenerator tokenGenerator, RequestService requestService, UserFacade userFacade) {
    this.queryRepository = queryRepository;
    this.tokenGenerator = tokenGenerator;
    this.requestService = requestService;
    this.userFacade = userFacade;
  }

  public <T> T invalidateToken(String token, Supplier<T> supplier) {
    if (this.queryRepository.existsByToken(token)) {
      this.requestService.deleteRequestByToken(token);
      return supplier.get();
    }

    throw new RequestNotFoundException();
  }

  public String createToken(ObjectId requesterId) {
    this.userFacade.ifExists(requesterId);
    return this.requestService.createRequest(this.tokenGenerator.makeToken(), requesterId);
  }

  public RequestQueryDto fetchToken(String token) {
    return this.queryRepository.findByToken(token).orElseThrow(RequestNotFoundException::new);
  }

  public Set<RequestQueryDto> fetchAllByRequesterId(ObjectId requesterId) {
    this.userFacade.ifExists(requesterId);
    return this.queryRepository.findAllByRequesterId(requesterId);
  }

}
