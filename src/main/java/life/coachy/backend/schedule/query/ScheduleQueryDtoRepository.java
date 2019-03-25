package life.coachy.backend.schedule.query;

import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;

public interface ScheduleQueryDtoRepository extends QueryFetchAllRepository<ScheduleQueryDto, ObjectId>, QueryFetchOneRepository<ScheduleQueryDto, ObjectId> {

}
