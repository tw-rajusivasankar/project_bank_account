package com.example.project_bank_account;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "Bank Accounts MicroService REST API Documentation",
				description = "Raju Learning Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Raju Sivasankar",
						email = "mobiledeveloper.code@gmail.com",
						url = "https//www.raju.learning"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https//www.raju.learning"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Raju Learning Accounts microservice REST API Documentation",
				url = "https://www.raju.learning"
		)
)
public class ProjectBankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBankAccountApplication.class, args);
	}

}