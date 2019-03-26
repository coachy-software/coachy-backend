package life.coachy.backend.board.domain;

import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;

class BoardCreator {

  Board from(BoardCreateCommandDto dto) {
    return Board.builder()
        .withName(dto.getName())
        .withLabels(dto.getLabels())
        .withOwnerId(dto.getOwnerId())
        .build();
  }

}
