package com.ebercruz.myrlux.back;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.swagger.v3.oas.models.info.Info;


@EnableCaching
@EnableScheduling
@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication(
		scanBasePackages = {
				"com.ebercruz.myrlux.back",
				"com.ebercruz.myrlux.back.api.service"
		}
)
public class MyrluxBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyrluxBackApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Myrlux API")
						.version("1.0")
						.description("Documentación de la API de Myrlux"));
	}

}
