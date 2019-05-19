package life.coachy.backend.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

  @Value("${frontend.uri}") private String allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowCredentials(true)
        .allowedOrigins("http://www.coachy.life")
        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
        .exposedHeaders("Location");
  }

}
