package life.coachy.backend.schedule;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.schedule.dto.ScheduleDtoMapperFactory;
import life.coachy.backend.schedule.dto.ScheduleGlobalDto;
import life.coachy.backend.user.UserFacade;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.CrudOperationsService;
import life.coachy.backend.util.dto.AbstractDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ScheduleCrudService implements CrudOperationsService<Schedule, ObjectId> {

  private ScheduleRepository repository;
  private ScheduleDtoMapperFactory mapperFactory;
  private UserFacade userFacade;

  @Autowired
  ScheduleCrudService(ScheduleRepository repository, ScheduleDtoMapperFactory mapperFactory, UserFacade userFacade) {
    this.repository = repository;
    this.mapperFactory = mapperFactory;
    this.userFacade = userFacade;
  }

  @Override
  public Optional<Schedule> findByName(String name) {
    Preconditions.checkNotNull(name, "Schedule entity name cannot be null");
    return this.repository.findByName(name);
  }

  @Override
  public Optional<Schedule> findById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");
    return this.repository.findById(objectId);
  }

  public Schedule save(Schedule entity) {
    Preconditions.checkNotNull(entity, "Schedule entity cannot be null");
    return this.repository.save(entity);
  }

  @Override
  public <S extends AbstractDto> Schedule save(S dto) {
    Preconditions.checkNotNull(dto, "Schedule DTO cannot be null");
    Schedule schedule = this.repository.save(this.mapperFactory.obtainEntity(dto));

    String[] chargePermissions = {"schedule." + schedule.getIdentifier() + ".read"};
    String[] coachPermissions = {
        "schedule." + schedule.getIdentifier() + ".read",
        "schedule." + schedule.getIdentifier() + ".update",
        "schedule." + schedule.getIdentifier() + ".delete"
    };

    this.userFacade.addPermissions(schedule.getCreator().getIdentifier(), coachPermissions);
    this.userFacade.addPermissions(schedule.getCharge().getIdentifier(), chargePermissions);

    return schedule;
  }

  @Override
  public void deleteById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");

    Schedule schedule = this.findById(objectId).get();
    UserDto charge = schedule.getCharge();
    UserDto creator = schedule.getCreator();

    String[] chargePermissionsToRemove = this.getAndMapUserPermissions(charge, objectId);
    String[] creatorPermissionsToRemove = this.getAndMapUserPermissions(creator, objectId);

    this.userFacade.removePermissions(charge.getIdentifier(), chargePermissionsToRemove);
    this.userFacade.removePermissions(creator.getIdentifier(), creatorPermissionsToRemove);

    this.repository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");
    return this.repository.existsById(objectId);
  }

  @Override
  public List<ScheduleGlobalDto> findAll() {
    return ScheduleMapper.INSTANCE.schedulesToScheduleGlobalDtos(this.repository.findAll());
  }

  @Override
  public List<ScheduleGlobalDto> findAll(Predicate predicate) {
    Preconditions.checkNotNull(predicate);
    return ScheduleMapper.INSTANCE
        .schedulesToScheduleGlobalDtos(Lists.newArrayList(this.repository.findAll(predicate)));
  }

  @Override
  public Page<ScheduleGlobalDto> findAll(Predicate predicate, Pageable pageable) {
    Preconditions.checkNotNull(predicate);
    Preconditions.checkNotNull(pageable);
    return ScheduleMapper.INSTANCE.schedulesToScheduleGlobalDtos(this.repository.findAll(predicate, pageable));
  }

  @Override
  public Page<ScheduleGlobalDto> findAll(Pageable pageable) {
    Preconditions.checkNotNull(pageable);
    return ScheduleMapper.INSTANCE.schedulesToScheduleGlobalDtos(this.repository.findAll(pageable));
  }

  private String[] getAndMapUserPermissions(UserDto user, ObjectId objectId) {
    return user.getPermissions().stream()
        .filter(permission -> permission.contains(objectId.toHexString()))
        .toArray(String[]::new);
  }

}
