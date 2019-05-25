package life.coachy.backend.headway

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import groovy.transform.CompileStatic
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDtoBuilder
import life.coachy.backend.headway.measurement.dto.MeasurementDtoBuilder
import org.bson.types.ObjectId

@CompileStatic
trait SampleHeadways {

  ObjectId sampleHeadwayId = ObjectId.get()

  HeadwayCreateCommandDto sampleHeadwayCreateCommandDto = HeadwayCreateCommandDtoBuilder.create()
      .withIdentifier(sampleHeadwayId)
      .withOwnerId(sampleHeadwayId)
      .withType("BUILD")
      .withMeasurements(Sets.newHashSet(
          MeasurementDtoBuilder.create().withId(ObjectId.get()).withName("test name").withValue("4").build()
      ))
      .withImages(Lists.newArrayList("http://coachy.life/image1.png", "http://coachy.life/image2.png"))
      .build();

}
