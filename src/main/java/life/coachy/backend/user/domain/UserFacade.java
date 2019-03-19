package life.coachy.backend.user.domain;

import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;

class UserFacade {

  private final UserOperationsService userOperationsService;
  private final UserCrudService userCrudService;
  private final UserCreator userCreator;

  public UserFacade(UserOperationsService userOperationsService, UserCrudService userCrudService, UserCreator userCreator) {
    this.userOperationsService = userOperationsService;
    this.userCrudService = userCrudService;
    this.userCreator = userCreator;
  }

  public void register(UserRegisterCommandDto dto) {
    this.userOperationsService.registerByDto(dto, () -> this.userCrudService.save(this.userCreator.from(dto)));
  }

}
