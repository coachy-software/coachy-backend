package life.coachy.backend.schedule.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface ScheduleRepository extends CommandRepository<Schedule, ObjectId> {

}
