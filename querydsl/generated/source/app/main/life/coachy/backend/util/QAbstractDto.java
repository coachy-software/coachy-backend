package life.coachy.backend.util;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractDto is a Querydsl query type for AbstractDto
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAbstractDto extends BeanPath<AbstractDto<? extends IdentifiableEntity<?>>> {

    private static final long serialVersionUID = 1524479848L;

    public static final QAbstractDto abstractDto = new QAbstractDto("abstractDto");

    public final StringPath entityName = createString("entityName");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractDto(String variable) {
        super((Class) AbstractDto.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractDto(Path<? extends AbstractDto> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractDto(PathMetadata metadata) {
        super((Class) AbstractDto.class, metadata);
    }

}

