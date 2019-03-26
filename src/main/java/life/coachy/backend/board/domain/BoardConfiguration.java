package life.coachy.backend.board.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BoardConfiguration {

  @Bean
  BoardFacade boardFacade(BoardService boardService) {
    BoardCreator creator = new BoardCreator();
    return new BoardFacade(boardService, creator);
  }

}
