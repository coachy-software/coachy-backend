package life.coachy.backend.request.domain;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RequestService {

  private final RequestRepository repository;

  @Autowired
  RequestService(RequestRepository repository) {
    this.repository = repository;
  }

  String createRequest(String token, ObjectId requesterId) {
    Request request = RequestBuilder.create()
        .withToken(token)
        .withRequesterId(requesterId)
        .build();

    this.repository.save(request);
    return token;
  }

  void deleteRequestByToken(String token) {
    this.repository.deleteByToken(token);
  }

}
