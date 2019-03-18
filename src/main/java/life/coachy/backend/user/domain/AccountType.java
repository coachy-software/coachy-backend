package life.coachy.backend.user.domain;

import life.coachy.backend.user.domain.dto.AccountTypeDto;

enum AccountType {

  CHARGE, COACH;

  AccountTypeDto dto() {
    return AccountTypeDto.valueOf(this.name());
  }

}
