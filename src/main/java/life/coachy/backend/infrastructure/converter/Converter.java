package life.coachy.backend.infrastructure.converter;

interface Converter<R, T> {

  default R convert(T value) {
    throw new UnsupportedOperationException();
  }

}
