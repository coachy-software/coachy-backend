package life.coachy.backend.schedule;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ScheduleRepository extends MongoRepository<Schedule, ObjectId> {

  Optional<Schedule> findByName(String name);

}
