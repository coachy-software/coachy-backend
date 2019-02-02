package life.coachy.backend.schedule.day;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleDayDto is a Querydsl query type for ScheduleDayDto
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QScheduleDayDto extends BeanPath<ScheduleDayDto> {

    private static final long serialVersionUID = 903896588L;

    public static final QScheduleDayDto scheduleDayDto = new QScheduleDayDto("scheduleDayDto");

    public final life.coachy.backend.util.QAbstractDto _super = new life.coachy.backend.util.QAbstractDto(this);

    public final StringPath entityName = createString("entityName");

    public final ListPath<life.coachy.backend.exercise.ExerciseDto, SimplePath<life.coachy.backend.exercise.ExerciseDto>> exercises = this.<life.coachy.backend.exercise.ExerciseDto, SimplePath<life.coachy.backend.exercise.ExerciseDto>>createList("exercises", life.coachy.backend.exercise.ExerciseDto.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath musclesPart = createString("musclesPart");

    public final StringPath name = createString("name");

    public final BooleanPath trainingDay = createBoolean("trainingDay");

    public QScheduleDayDto(String variable) {
        super(ScheduleDayDto.class, forVariable(variable));
    }

    public QScheduleDayDto(Path<? extends ScheduleDayDto> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleDayDto(PathMetadata metadata) {
        super(ScheduleDayDto.class, metadata);
    }

}

