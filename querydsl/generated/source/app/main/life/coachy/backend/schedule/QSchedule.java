package life.coachy.backend.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = -2061325833L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final BooleanPath active = createBoolean("active");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final life.coachy.backend.user.QUserDto creator;

    public final ListPath<life.coachy.backend.schedule.day.ScheduleDayDto, life.coachy.backend.schedule.day.QScheduleDayDto> days = this.<life.coachy.backend.schedule.day.ScheduleDayDto, life.coachy.backend.schedule.day.QScheduleDayDto>createList("days", life.coachy.backend.schedule.day.ScheduleDayDto.class, life.coachy.backend.schedule.day.QScheduleDayDto.class, PathInits.DIRECT2);

    public final org.bson.types.QObjectId identifier;

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> updatedAt = createDateTime("updatedAt", java.util.Date.class);

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new life.coachy.backend.user.QUserDto(forProperty("creator"), inits.get("creator")) : null;
        this.identifier = inits.isInitialized("identifier") ? new org.bson.types.QObjectId(forProperty("identifier")) : null;
    }

}

