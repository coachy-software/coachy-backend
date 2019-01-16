/*
 * MIT License
 *
 * Copyright (c) 2018 Coachy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package life.coachy.backend.user;

import javax.validation.Valid;
import life.coachy.backend.util.AbstractCrudController;
import life.coachy.backend.util.security.AuthenticatedUser;
import life.coachy.backend.util.security.RequiresAuthenticated;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
class UserController extends AbstractCrudController<User, ObjectId, UserCrudDto, UserRegistrationDto> {

  private final UserService userService;

  @Autowired
  protected UserController(UserService userService) {
    super(userService);
    this.userService = userService;
  }

  @RequiresAuthenticated
  @GetMapping("/me")
  public ResponseEntity<User> me(@AuthenticatedUser User user) {
    return ResponseEntity.ok(user);
  }

  @Override
  @Valid
  protected ResponseEntity<?> update(@RequestBody UserCrudDto dto, @PathVariable ObjectId id,
      BindingResult result) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.update(dto, id, result);
  }


  @Override
  protected ResponseEntity<UserCrudDto> partialUpdate(@RequestBody UserCrudDto dto, @PathVariable ObjectId id) {
    if (this.userService.existsByEmail(dto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return super.partialUpdate(dto, id);
  }

}
