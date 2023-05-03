package dwr.MiniaturowaTablica.api;

import dwr.MiniaturowaTablica.api.ZTM.ztmRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ApiApplication {

   public static void main(String[] args) throws IOException {
      SpringApplication.run(ApiApplication.class, args);
      ztmRepository.loadStaticDataToDB();
   }
}
