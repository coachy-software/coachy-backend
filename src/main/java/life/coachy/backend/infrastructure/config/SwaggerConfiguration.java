package life.coachy.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

  private static final String PATH_REGEX = "(?!.*error).*$";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.regex(PATH_REGEX))
        .build()
        .apiInfo(new ApiInfoBuilder()
            .title("Coachy API Docs")
            .version("BETA")
            .license("MIT")
            .licenseUrl("https://github.com/coachy-software/coachy-backend/blob/master/LICENSE")
            .contact(new Contact("Coachy", "http://coachy.life/", "support@coachy.life"))
            .build()
        );
  }

}
