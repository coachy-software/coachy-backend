package life.coachy.backend.profile.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface ProfileRepository extends CommandRepository<Profile, ObjectId> {

}
