package life.coachy.backend.headway

import com.google.common.collect.Lists
import groovy.transform.CompileStatic
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDtoBuilder
import org.bson.types.ObjectId

@CompileStatic
trait SampleHeadways {

  ObjectId sampleHeadwayId = ObjectId.get()

  HeadwayCreateCommandDto sampleHeadwayCreateCommandDto = HeadwayCreateCommandDtoBuilder.create()
      .withIdentifier(sampleHeadwayId)
      .withOwnerId(sampleHeadwayId)
      .withNeckMeasurement(30)
      .withArmMeasurement(30)
      .withForearmMeasurement(30)
      .withWristMeasurement(30)
      .withChestMeasurement(30)
      .withWaistMeasurement(30)
      .withThighMeasurement(30)
      .withCalfMeasurement(30)
      .withImages(Lists.newArrayList("http://coachy.life/image1.png", "http://coachy.life/image2.png"))
      .build();

}
