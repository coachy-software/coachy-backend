package life.coachy.backend.user;

import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserRegistrationDto;
import life.coachy.backend.user.dto.UserUpdateDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends MapStructMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User userRegistrationDtoToUser(UserRegistrationDto dto);

  User userUpdateDtoToUser(UserUpdateDto dto);

  @Mapping(target = "identifier", source = "dto.identifier")
  User userDtoToUser(UserDto dto);

}
