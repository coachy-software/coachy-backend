package life.coachy.backend.headway.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface HeadwayRepository extends CommandRepository<Headway, ObjectId> {

}
