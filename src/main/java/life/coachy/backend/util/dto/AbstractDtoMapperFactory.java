package life.coachy.backend.util.dto;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import life.coachy.backend.util.IdentifiableEntity;
import life.coachy.backend.util.MapStructMapper;
import life.coachy.backend.util.StringUtil;
import org.reflections.Reflections;

public abstract class AbstractDtoMapperFactory<C extends MapStructMapper> {

  private final Reflections reflections;
  private final C mapper;

  protected AbstractDtoMapperFactory(C mapper, Reflections reflections) {
    this.mapper = mapper;
    this.reflections = reflections;
  }

  public <T extends IdentifiableEntity<?>, D extends AbstractDto> T obtainEntity(D dto) {
    Preconditions.checkNotNull(this.mapper, "Map struct mapper cannot be null");
    Preconditions.checkNotNull(this.reflections, "Reflections cannot be null");
    Preconditions.checkNotNull(dto, "Dto cannot be null");

    Set<Class<?>> annotatedTypes = this.reflections.getTypesAnnotatedWith(DataTransferObject.class, true);

    for (Class<?> clazz : annotatedTypes) {
      DataTransferObject dtoAnnotation = clazz.getAnnotation(DataTransferObject.class);

      if (dtoAnnotation.mapperClass().getName().equals(this.mapper.getClass().getInterfaces()[0].getName())) {
        if (dto.getClass().getName().equals(clazz.getName())) {
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
