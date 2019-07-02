package life.coachy.backend.infrastructure.converter;

@FunctionalInterface
public interface Converter<R, T> {

  R convert(T value);

}
