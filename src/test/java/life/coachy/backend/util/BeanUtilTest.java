package life.coachy.backend.util;

import life.coachy.backend.util.crud.TestEntity;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class BeanUtilTest {

  @Test
  public void copyNonNullProperties() {
    ObjectId id = ObjectId.get();

    TestEntity testEntity = new TestEntity(id, "testUsername", "something fucked up");
    TestEntity testEntityEdited = new TestEntity(null, "testUsernameButEdited", null);

    BeanUtil.copyNonNullProperties(testEntity, testEntityEdited);

    assertAll(
        () -> assertNotNull(testEntity),
        () -> assertNotNull(testEntityEdited),
        () -> assertEquals(testEntityEdited.getUsername(), testEntity.getUsername()),
        () -> assertEquals(testEntityEdited.getIdentifier(), testEntity.getIdentifier()),
        () -> assertEquals(testEntityEdited.getSomething(), testEntity.getSomething())
    );
  }

}
