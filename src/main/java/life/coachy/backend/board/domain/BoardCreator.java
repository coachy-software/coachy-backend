package life.coachy.backend.board.domain;

import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateCommandDto;

class BoardCreator {

  Board from(BoardCreateCommandDto dto) {
    return Board.builder()
        .withName(dto.getName())
        .withLabels(dto.getLabels())
        .withOwnerId(dto.getOwnerId())
        .build();
  }

  Board from(BoardUpdateCommandDto dto) {
    return Board.builder()
        .withName(dto.getName())
        .withLabels(dto.getLabels())
        .build();
  }

}
