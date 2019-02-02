package life.coachy.backend.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserDto is a Querydsl query type for UserDto
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserDto extends BeanPath<UserDto> {

    private static final long serialVersionUID = 927076200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDto userDto = new QUserDto("userDto");

    public final life.coachy.backend.util.QAbstractDto _super = new life.coachy.backend.util.QAbstractDto(this);

    public final EnumPath<AccountType> accountType = createEnum("accountType", AccountType.class);

    public final StringPath avatar = createString("avatar");

    public final StringPath displayName = createString("displayName");

    public final StringPath email = createString("email");

    public final StringPath entityName = createString("entityName");

    public final org.bson.types.QObjectId identifier;

    public final StringPath password = createString("password");

    public final SetPath<String, StringPath> roles = this.<String, StringPath>createSet("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUserDto(String variable) {
        this(UserDto.class, forVariable(variable), INITS);
    }

    public QUserDto(Path<? extends UserDto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDto(PathMetadata metadata, PathInits inits) {
        this(UserDto.class, metadata, inits);
    }

    public QUserDto(Class<? extends UserDto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.identifier = inits.isInitialized("identifier") ? new org.bson.types.QObjectId(forProperty("identifier")) : null;
    }

}

