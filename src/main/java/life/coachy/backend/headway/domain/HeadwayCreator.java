package life.coachy.backend.headway.domain;

import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;

class HeadwayCreator {

  Headway from(HeadwayCreateCommandDto dto) {
    return HeadwayBuilder.create()
        .withIdentifier(dto.getIdentifier())
        .withOwnerId(dto.getOwnerId())
        .withNeckMeasurement(dto.getNeckMeasurement())
        .withArmMeasurement(dto.getArmMeasurement())
        .withForearmMeasurement(dto.getForearmMeasurement())
        .withWristMeasurement(dto.getWristMeasurement())
        .withChestMeasurement(dto.getChestMeasurement())
        .withWaistMeasurement(dto.getWaistMeasurement())
        .withThighMeasurement(dto.getThighMeasurement())
        .withCalfMeasurement(dto.getCalfMeasurement())
        .withImages(dto.getImages())
        .build();
  }

}
