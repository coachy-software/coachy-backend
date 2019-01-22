package life.coachy.backend.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation annotation to validate that 2 fields have the same value. An array of fields and their matching
 * confirmation fields can be supplied.
 *
 * Example, compare 1 pair of fields:
 *
 * @Match(first = "password", second = "confirmPassword", message = "The password fields must match")
 *
 * Example, compare more than 1 pair of fields:
 * @Match.List({
 * @Match(first = "password", second = "confirmPassword", message = "The password fields must match"),
 * @Match(first = "email", second = "confirmEmail", message = "The email fields must match")})
 */

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchValidator.class)
@Documented
public @interface Match {

  String message() default "{constraints.fieldmatch}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * @return The first field
   */
  String first();

  /**
   * @return The second field
   */
  String second();

  /**
   * Defines several <code>@FieldMatch</code> annotations on the same element
   *
   * @see Match
   */
  @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    Match[] value();

  }

}