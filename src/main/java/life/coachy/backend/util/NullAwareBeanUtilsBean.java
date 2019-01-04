package life.coachy.backend.util;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;

class NullAwareBeanUtilsBean extends BeanUtilsBean {

  @Override
  public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
    if (value == null) {
      return;
    }

    super.copyProperty(bean, name, value);
  }

}
