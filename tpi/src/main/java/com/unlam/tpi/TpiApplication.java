package com.unlam.tpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://www.mercadojunior.com.ar", "http://www.mercadojunior.com.ar",
								"http://localhost", "https://localhost", "http://127.0.0.1", "https://127.0.0.1")
						.allowedMethods("*").allowedHeaders("*");
			}
		};
	}
}
