package life.coachy.backend.user.domain;

import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import life.coachy.backend.user.domain.dto.UserUpdateEntireEntityCommandDto;
import org.bson.types.ObjectId;

public class UserFacade {

  private final UserOperationsService operationsService;
  private final UserCrudService crudService;
  private final UserCreator creator;

  public UserFacade(UserOperationsService operationsService, UserCrudService crudService, UserCreator creator) {
    this.operationsService = operationsService;
    this.crudService = crudService;
    this.creator = creator;
  }

  public void register(UserRegisterCommandDto dto) {
    this.operationsService.checkIfUsernameAndEmailAlreadyExists(dto.getUsername(), dto.getEmail(), () -> this.crudService.save(this.creator.from(dto)));
  }

  public void update(ObjectId id, UserUpdateEntireEntityCommandDto dto) {
    this.operationsService.checkIfUsernameAlreadyExists(id, dto.getUsername(), () -> this.crudService.convertPropertiesToMapAndSave(id, dto));
  }

  public void delete(ObjectId id) {
    this.operationsService.checkIfExists(id, () -> this.crudService.delete(id));
  }

  public void givePermissions(ObjectId id, String... permissions) {
    this.operationsService.addPermissions(id, permissions);
  }

  public void nullifyPermissions(ObjectId id, ObjectId permissionId) {
    this.operationsService.removePermissions(id, permissionId);
  }

  public void resetPassword(String email, String newPassword) {
    this.operationsService.resetPassword(email, newPassword);
  }
}
