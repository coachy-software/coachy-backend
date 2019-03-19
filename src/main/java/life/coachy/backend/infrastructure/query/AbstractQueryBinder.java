package life.coachy.backend.infrastructure.query;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public abstract class AbstractQueryBinder<T extends EntityPath<?>> implements QuerydslBinderCustomizer<T> {

  @Override
  public void customize(QuerydslBindings bindings, T root) {
    bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

}
