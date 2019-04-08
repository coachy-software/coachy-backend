package life.coachy.backend.infrastructure.converter;

@FunctionalInterface
interface Converter<R, T> {

  R convert(T value);

}
