package com.ebercruz.myrluxb.deepseek.api;

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
				"com.ebercruz.myrluxb.deepseek.api",
				"com.ebercruz.myrluxb.deepseek.api.service"
		}
)
public class MyrluxDeepseekApiBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyrluxDeepseekApiBackApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Myrlux-deepseek-API")
						.version("1.0")
						.description("Documentación de la API de Myrlux"));
	}

}
