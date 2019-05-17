package life.coachy.backend.headway.domain;

import java.util.Set;
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

  void save(Headway headway) {
    this.headwayRepository.save(headway);
  }

  void deleteById(ObjectId id) {
    this.headwayRepository.deleteById(id);
  }

  Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId id) {
    return this.headwayQueryRepository.findAllByOwnerIdOrderByCreatedAtDesc(id);
  }

}
