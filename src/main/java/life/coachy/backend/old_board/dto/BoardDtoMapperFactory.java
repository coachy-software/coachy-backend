package life.coachy.backend.old_board.dto;

import life.coachy.backend.old_board.BoardMapper;
import life.coachy.backend.infrastructure.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class BoardDtoMapperFactory extends AbstractDtoMapperFactory<BoardMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.old_board.dto");

  public BoardDtoMapperFactory() {
    super(BoardMapper.INSTANCE, REFLECTIONS);
  }

}
