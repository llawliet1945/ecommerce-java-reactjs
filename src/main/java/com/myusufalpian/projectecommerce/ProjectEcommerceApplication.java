package com.myusufalpian.projectecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Definition for Back-End Ecommerce Service",
				version = "1.0.0"
		)
)

public class ProjectEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectEcommerceApplication.class, args);
	}

}
