package life.coachy.backend.board;

import life.coachy.backend.board.dto.BoardDto;
import life.coachy.backend.board.dto.BoardUpdateDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper extends MapStructMapper {

  BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

  Board boardDtoToBoard(BoardDto dto);

  @Mapping(source = "dto.ownerId", target = "owner.identifier")
  Board boardUpdateDtoToBoard(BoardUpdateDto dto);

}
