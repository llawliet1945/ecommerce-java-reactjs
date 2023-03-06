package com.myusufalpian.projectecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Definition for Back-End Ecommerce Service",
				version = "1.0.0"
		)
)
@EnableResourceServer
public class ProjectEcommerceApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectEcommerceApplication.class, args);
	}

}
