package life.coachy.backend.schedule.domain;

import com.querydsl.core.types.Predicate;
import java.net.URI;
import java.util.List;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.schedule.query.ScheduleQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ScheduleFacade {

  private final QueryOperationsFactory queryOperationsFactory;
  private final ScheduleQueryRepository queryDtoRepository;
  private final ScheduleService service;
  private final ScheduleCreator creator;
  private final UserFacade userFacade;

  public ScheduleFacade(ScheduleQueryRepository queryDtoRepository, QueryOperationsFactory queryOperationsFactory, ScheduleService service,
      ScheduleCreator creator, UserFacade userFacade) {
    this.queryDtoRepository = queryDtoRepository;
    this.queryOperationsFactory = queryOperationsFactory;
    this.service = service;
    this.creator = creator;
    this.userFacade = userFacade;
  }

  public List<ScheduleQueryDto> fetchAll(Predicate predicate, Pageable pageable) {
    return this.queryOperationsFactory.obtainOperation(predicate, pageable, this.queryDtoRepository);
  }

  public void delete(ObjectId id) {
    this.service.delete(this.userFacade, id);
  }

  public void update(ObjectId id, ScheduleUpdateCommandDto dto) {
    this.service.update(id, this.creator.from(dto));
  }

  public ScheduleQueryDto fetchOne(ObjectId id) {
    return this.service.fetchOne(id);
  }

  public URI create(ScheduleCreateCommandDto dto) {
    Schedule schedule = this.service.save(this.creator.from(dto));
    this.service.givePermissions(this.userFacade, schedule, dto);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.SCHEDULES + "/{id}").buildAndExpand(schedule.identifier).toUri();
  }

}
