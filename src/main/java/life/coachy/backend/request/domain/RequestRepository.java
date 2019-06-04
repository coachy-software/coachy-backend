package life.coachy.backend.request.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface RequestRepository extends CommandRepository<Request, ObjectId> {

  void deleteByToken(String token);

}
