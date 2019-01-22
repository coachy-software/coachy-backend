package life.coachy.backend.util.validation;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringEnumerationValidator implements ConstraintValidator<StringEnumeration, String> {

  private Set<Object> availableEnumNames;

  @Override
  public void initialize(StringEnumeration stringEnumeration) {
    Class<? extends Enum<?>> enumSelected = stringEnumeration.enumClass();
    Set<? extends Enum<?>> enumInstances = Sets.newHashSet(enumSelected.getEnumConstants());

    this.availableEnumNames = enumInstances.stream()
        .map(Enum::name)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || this.availableEnumNames.contains(value);
  }

}