package life.coachy.backend;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class BackendConfiguration {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");

    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    factoryBean.setValidationMessageSource(this.messageSource());

    return factoryBean;
  }

}

@Configuration
@EnableAsync
@Profile("!test")
class EmailConfiguration {

}

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
