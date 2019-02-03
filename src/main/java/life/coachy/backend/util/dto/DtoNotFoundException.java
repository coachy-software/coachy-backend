package life.coachy.backend.util.dto;

class DtoNotFoundException extends RuntimeException {

  public DtoNotFoundException() {
  }

  public DtoNotFoundException(String message) {
    super(message);
  }

}
