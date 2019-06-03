package life.coachy.backend.request.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class RequestBuilder implements Buildable<Request> {

  ObjectId identifier;
  String token;
  long version;
  LocalDateTime createdAt;

  private RequestBuilder() {}

  public static RequestBuilder create() {
    return new RequestBuilder();
  }

  public RequestBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public RequestBuilder withToken(String token) {
    this.token = token;
    return this;
  }

  public RequestBuilder withVersion(long version) {
    this.version = version;
    return this;
  }

  public RequestBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public Request build() {
    return new Request(this);
  }

}
