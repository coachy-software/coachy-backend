package life.coachy.backend.board.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface BoardRepository extends CommandRepository<Board, ObjectId>, BoardRepositoryExtension {

}
