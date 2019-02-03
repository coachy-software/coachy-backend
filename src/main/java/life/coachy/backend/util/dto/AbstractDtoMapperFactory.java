package life.coachy.backend.util.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import life.coachy.backend.util.IdentifiableEntity;
import life.coachy.backend.util.MapStructMapper;
import life.coachy.backend.util.StringUtil;
import org.reflections.Reflections;

public abstract class AbstractDtoMapperFactory<C extends MapStructMapper> {

  private static final Reflections REFLECTIONS = new Reflections();
  private final C mapper;

  protected AbstractDtoMapperFactory(C mapper) {
    this.mapper = mapper;
  }

  public <T extends IdentifiableEntity<?>, D extends AbstractDto> T obtainEntity(D dto) { // TODO validation
    Set<Class<?>> annotatedTypes = REFLECTIONS.getTypesAnnotatedWith(DataTransferObject.class);

    for (Class<?> clazz : annotatedTypes) {
      DataTransferObject dtoAnnotation = clazz.getAnnotation(DataTransferObject.class);

      if (dtoAnnotation.mapperClass().equals(this.mapper.getClass())) {
        if (dto.getClass().equals(clazz)) {
          String methodName = StringUtil.lowerCaseAt(0, dto.getClass().getSimpleName())
              + "To"
              + StringUtil.upperCaseAt(0, dtoAnnotation.entityName());

          try {
            return (T) dtoAnnotation.mapperClass().getMethod(methodName, dto.getClass()).invoke(this.mapper, dto);
          } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
      }
    }

    throw new DtoNotFoundException("Data transfer object named " + dto + " cannot be found!");
  }

}
