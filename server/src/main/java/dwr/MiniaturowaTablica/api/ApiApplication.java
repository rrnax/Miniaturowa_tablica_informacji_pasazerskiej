package dwr.MiniaturowaTablica.api;

import dwr.MiniaturowaTablica.api.ZTM_WARSAW.ZTMWarsawRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ApiApplication {

   public static void main(String[] args) throws IOException {
      SpringApplication.run(ApiApplication.class, args);
      ZTMWarsawRepository.getAllDisplays();
   }
}
