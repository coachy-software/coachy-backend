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

import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserService implements CrudOperationsService<User, ObjectId> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<User> findByName(String name) {
    return this.userRepository.findByUsername(name);
  }

  @Override
  public Optional<User> findById(ObjectId objectId) {
    return this.userRepository.findById(objectId);
  }

  Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public <S extends User> S save(S entity) {
    Optional<User> databaseUser = this.findByName(entity.getUsername());
    if (databaseUser.isPresent() && !entity.getPassword().equals(databaseUser.get().getPassword())) {
      this.passwordEncoder.encode(entity.getPassword());
    }

    return this.userRepository.save(entity);
  }

  @Override
  public void deleteById(ObjectId objectId) {
    this.userRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.userRepository.existsById(objectId);
  }

  boolean existsByEmail(String email) {
    return this.userRepository.existsByEmail(email);
  }

  User savePassword(User user, String password) {
    user.setPassword(this.passwordEncoder.encode(password));
    return this.userRepository.save(user);
  }

}
