package life.coachy.backend.headway.domain;

import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;

class HeadwayCreator {

  Headway from(HeadwayCreateCommandDto dto) {
    return HeadwayBuilder.create()
        .withIdentifier(dto.getIdentifier())
        .withOwnerId(dto.getOwnerId())
        .withMeasurements(dto.getMeasurements())
        .withImages(dto.getImages())
        .build();
  }

}
