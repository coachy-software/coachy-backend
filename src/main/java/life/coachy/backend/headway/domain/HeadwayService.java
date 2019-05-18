package life.coachy.backend.headway.domain;

import java.util.Set;
import life.coachy.backend.headway.domain.exception.HeadwayNotFoundException;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.headway.query.HeadwayQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class HeadwayService {

  private final HeadwayRepository headwayRepository;
  private final HeadwayQueryRepository headwayQueryRepository;

  @Autowired
  HeadwayService(HeadwayRepository headwayRepository, HeadwayQueryRepository headwayQueryRepository) {
    this.headwayRepository = headwayRepository;
    this.headwayQueryRepository = headwayQueryRepository;
  }

  Headway save(Headway headway) {
    return this.headwayRepository.save(headway);
  }

  void deleteById(ObjectId id) {
    if (!this.headwayQueryRepository.existsByIdentifier(id)) {
      throw new HeadwayNotFoundException();
    }

    this.headwayRepository.deleteById(id);
  }

  HeadwayQueryDto fetchOne(ObjectId id) {
    return this.headwayQueryRepository.findById(id).orElseThrow(HeadwayNotFoundException::new);
  }

  Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId id) {
    return this.headwayQueryRepository.findAllByOwnerIdOrderByCreatedAtDesc(id);
  }

}
