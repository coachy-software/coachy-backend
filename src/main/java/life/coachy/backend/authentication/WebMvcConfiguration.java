package life.coachy.backend.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:8080")
        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
  }

}
