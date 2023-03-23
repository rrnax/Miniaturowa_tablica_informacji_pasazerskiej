package dwr.MiniaturowaTablica.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
class CorsConfiguration implements WebMvcConfigurer {
   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
            .allowCredentials(true)
            .allowedHeaders("*")
            .allowedOrigins(
                  "http://localhost:10000"
            )
            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
   }
}