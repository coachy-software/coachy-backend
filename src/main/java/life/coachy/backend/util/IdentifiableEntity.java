package life.coachy.backend.util;

@FunctionalInterface
public interface IdentifiableEntity<ID> {

  ID getIdentifier();

}
