package life.coachy.backend.board.domain;

import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BoardConfiguration {

  @Bean
  BoardFacade boardFacade(BoardService boardService, UserFacade userFacade) {
    BoardCreator creator = new BoardCreator();
    return new BoardFacade(boardService, creator, userFacade);
  }

}
