package life.coachy.backend.util;

import java.lang.reflect.Field;
import java.util.Collection;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public final class BeanUtil {

  private BeanUtil() {
  }

  public static <T> T copyNonNullProperties(T destination, T origin) {
    if (origin == null || destination == null || destination.getClass() != origin.getClass()) {
      return null;
    }

    BeanWrapper destinationWrapper = new BeanWrapperImpl(destination);
    BeanWrapper originWrapper = new BeanWrapperImpl(origin);

    for (Field property : destination.getClass().getDeclaredFields()) {
      Object providedObject = originWrapper.getPropertyValue(property.getName());
      if (providedObject != null && !(providedObject instanceof Collection<?>)) {
        destinationWrapper.setPropertyValue(property.getName(), providedObject);
      }
    }
    return destination;
  }

}
