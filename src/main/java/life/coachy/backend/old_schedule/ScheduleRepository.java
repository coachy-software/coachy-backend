package life.coachy.backend.old_schedule;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface ScheduleRepository extends MongoRepository<Schedule, ObjectId>, QuerydslPredicateExecutor<Schedule> {

  Optional<Schedule> findByName(String name);

}
