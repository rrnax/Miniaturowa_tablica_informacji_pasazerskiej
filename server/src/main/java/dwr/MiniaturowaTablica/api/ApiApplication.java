package dwr.MiniaturowaTablica.api;

import dwr.MiniaturowaTablica.api.models.Depot;
import dwr.MiniaturowaTablica.api.repository.DepotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(DepotRepository depotRepository){
		return args -> {
			Depot depot = new Depot("nazwa-testowa");
			depotRepository.save(depot);
		};

	}
}
