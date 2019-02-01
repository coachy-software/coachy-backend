package life.coachy.backend.exercise.template;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExerciseTemplate is a Querydsl query type for ExerciseTemplate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExerciseTemplate extends EntityPathBase<ExerciseTemplate> {

    private static final long serialVersionUID = -516972985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExerciseTemplate exerciseTemplate = new QExerciseTemplate("exerciseTemplate");

    public final StringPath briefDescription = createString("briefDescription");

    public final ListPath<String, StringPath> exampleImages = this.<String, StringPath>createList("exampleImages", String.class, StringPath.class, PathInits.DIRECT2);

    public final org.bson.types.QObjectId identifier;

    public final StringPath name = createString("name");

    public final BooleanPath verified = createBoolean("verified");

    public QExerciseTemplate(String variable) {
        this(ExerciseTemplate.class, forVariable(variable), INITS);
    }

    public QExerciseTemplate(Path<? extends ExerciseTemplate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExerciseTemplate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExerciseTemplate(PathMetadata metadata, PathInits inits) {
        this(ExerciseTemplate.class, metadata, inits);
    }

    public QExerciseTemplate(Class<? extends ExerciseTemplate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.identifier = inits.isInitialized("identifier") ? new org.bson.types.QObjectId(forProperty("identifier")) : null;
    }

}

