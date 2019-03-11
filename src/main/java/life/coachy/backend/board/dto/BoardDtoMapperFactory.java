package life.coachy.backend.board.dto;

import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class BoardDtoMapperFactory extends AbstractDtoMapperFactory<BoardMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.user.dto");

  public BoardDtoMapperFactory() {
    super(BoardMapper.INSTANCE, REFLECTIONS);
  }

}
