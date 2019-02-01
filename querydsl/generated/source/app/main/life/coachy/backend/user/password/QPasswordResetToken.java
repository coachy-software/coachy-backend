package life.coachy.backend.user.password;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPasswordResetToken is a Querydsl query type for PasswordResetToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPasswordResetToken extends EntityPathBase<PasswordResetToken> {

    private static final long serialVersionUID = -1236942676L;

    public static final QPasswordResetToken passwordResetToken = new QPasswordResetToken("passwordResetToken");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath email = createString("email");

    public final StringPath identifier = createString("identifier");

    public final StringPath token = createString("token");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPasswordResetToken(String variable) {
        super(PasswordResetToken.class, forVariable(variable));
    }

    public QPasswordResetToken(Path<? extends PasswordResetToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPasswordResetToken(PathMetadata metadata) {
        super(PasswordResetToken.class, metadata);
    }

}

