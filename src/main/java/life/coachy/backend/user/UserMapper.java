package life.coachy.backend.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto userToUserDto(User user);

  User userDtoToUser(UserDto dto);

}
