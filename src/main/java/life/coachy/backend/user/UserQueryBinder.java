package life.coachy.backend.user;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

class UserQueryBinder implements QuerydslBinderCustomizer<QUser> {

  @Override
  public void customize(QuerydslBindings bindings, QUser root) {
    bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

}
