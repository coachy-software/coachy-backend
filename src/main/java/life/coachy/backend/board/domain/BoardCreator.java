package life.coachy.backend.board.domain;

import com.google.common.collect.Sets;
import life.coachy.backend.board.domain.dto.BoardCreateCommandDto;
import life.coachy.backend.board.domain.dto.BoardUpdateCommandDto;
import life.coachy.backend.board.query.BoardQueryDto;

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

  Board from(BoardQueryDto dto) {
    return Board.builder()
        .withName(dto.getName())
        .withLabels(Sets.newLinkedHashSet(dto.getLabels()))
        .withOwnerId(dto.getOwnerId())
        .build();
  }

}
