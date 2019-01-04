package life.coachy.backend.user;

import life.coachy.backend.util.AbstractCrudController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
class UserController extends AbstractCrudController<User, ObjectId, UserCrudDto> {

  protected UserController(@Autowired UserService userService) {
    super(userService);
  }

}
