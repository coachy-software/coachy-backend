package life.coachy.backend.infrastructure.validation;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class MatchValidator implements ConstraintValidator<Match, Object> {

  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize(Match constraintAnnotation) {
    this.firstFieldName = constraintAnnotation.first();
    this.secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Object firstObj = BeanUtils.getProperty(value, this.firstFieldName);
      Object secondObj = BeanUtils.getProperty(value, this.secondFieldName);

      return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
      ex.printStackTrace();
    }
    return true;
  }

}
