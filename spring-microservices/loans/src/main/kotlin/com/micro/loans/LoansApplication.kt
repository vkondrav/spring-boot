package com.micro.loans

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@OpenAPIDefinition(
    info = Info(
        title = "Loans API",
        description = "Loans API",
        version = "1.0",
        contact = Contact(
            name = "Loans Microservice Team",
            email = "loans@microservices.com",
        ),
        license = License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "Loans Wiki",
        url = "https://wiki.microservices.com/cards"
    )
)
@ConfigurationPropertiesScan
class LoansApplication

fun main(args: Array<String>) {
    runApplication<LoansApplication>(*args)
}
