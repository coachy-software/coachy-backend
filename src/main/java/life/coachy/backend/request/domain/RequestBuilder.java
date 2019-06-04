package life.coachy.backend.request.domain;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class RequestBuilder implements Buildable<Request> {

  ObjectId requesterId;
  String token;

  private RequestBuilder() {}

  public static RequestBuilder create() {
    return new RequestBuilder();
  }

  RequestBuilder withToken(String token) {
    this.token = token;
    return this;
  }

  RequestBuilder withRequesterId(ObjectId requesterId) {
    this.requesterId = requesterId;
    return this;
  }

  @Override
  public Request build() {
    return new Request(this);
  }

}
