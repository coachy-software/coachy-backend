package life.coachy.backend.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.test.annotation.IfProfileValue;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@IfProfileValue(name = "spring.profiles.active", value = "nonCI")
public @interface UncompilableByCI {

}
