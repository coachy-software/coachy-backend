package life.coachy.backend.util.crud;

import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCrudService implements CrudOperationsService<TestEntity, ObjectId> {

  TestRepository testRepository;

  @Autowired
  TestCrudService(TestRepository testRepository) {
    this.testRepository = testRepository;
  }

  @Override
  public Optional<TestEntity> findByName(String name) {
    return this.testRepository.findByName(name);
  }

  @Override
  public Optional<TestEntity> findById(ObjectId objectId) {
    return this.testRepository.findById(objectId);
  }

  @Override
  public List<TestEntity> findAll() {
    return this.testRepository.findAll();
  }

  @Override
  public <S extends TestEntity> S save(S entity) {
    return this.testRepository.save(entity);
  }

  @Override
  public void deleteById(ObjectId objectId) {
    this.testRepository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.testRepository.existsById(objectId);
  }

}