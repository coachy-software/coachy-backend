package life.coachy.backend.user.domain;

import life.coachy.backend.user.domain.dto.UserChangePasswordCommandDto;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import life.coachy.backend.user.domain.dto.UserUpdateCommandDto;
import life.coachy.backend.user.query.UserQueryDto;
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
    this.operationsService.checkIfUsernameOrEmailExists(dto.getUsername(), dto.getEmail(), () -> {
      User user = this.crudService.save(this.creator.from(dto));
      this.operationsService.addPermissions(user.identifier,
          "user." + user.identifier + ".update",
          "user." + user.identifier + ".delete",
          "user." + user.identifier + ".read");
    });
  }

  public void update(ObjectId id, UserUpdateCommandDto dto) {
    this.operationsService.checkIfUsernameOrEmailCanBeUpdated(id, dto.getUsername(), dto.getEmail(),
        (queryDto) -> this.crudService.update(queryDto, this.creator.from(dto)));
  }

  public void delete(ObjectId id) {
    this.operationsService.ifExists(id, () -> this.crudService.delete(id));
  }

  public void givePermissions(ObjectId id, String... permissions) {
    this.operationsService.addPermissions(id, permissions);
  }

  public void givePermissions(String username, String... permissions) {
    this.operationsService.addPermissions(username, permissions);
  }

  public void nullifyPermissions(ObjectId userId, ObjectId permissionId) {
    this.operationsService.removePermissions(userId, permissionId);
  }

  public void resetPassword(String email, String newPassword) {
    this.operationsService.resetPassword(email, newPassword);
  }

  public void updateBoardId(ObjectId boardId, ObjectId userId) {
    this.operationsService.updateBoardId(boardId, userId);
  }

  public void changePassword(UserQueryDto userQueryDto, UserChangePasswordCommandDto dto) {
    this.operationsService.validateAndChangePassword(userQueryDto, dto);
  }

  public void ifExists(ObjectId id) {
    this.operationsService.checkIfExists(id);
  }

  public void ifExists(ObjectId id, Runnable runnable) {
    this.operationsService.ifExists(id, runnable);
  }

}
